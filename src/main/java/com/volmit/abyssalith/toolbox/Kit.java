/*
 * Abyssalith is a Discord Bot for Volmit Software's Community
 * Copyright (c) 2021 VolmitSoftware (Arcane Arts)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.volmit.abyssalith.toolbox;

import art.arcane.quill.cache.AtomicCache;
import art.arcane.quill.execution.J;
import art.arcane.quill.io.FileWatcher;
import art.arcane.quill.io.IO;
import art.arcane.quill.json.JSONObject;
import art.arcane.quill.logging.L;
import com.google.gson.Gson;
import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.util.Range;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public class Kit extends ListenerAdapter {


    public void envInject() {
        env("BOT_DUMMY_PORT", (f) -> dummyPort = Integer.parseInt(f));
        env("BOT_TOKEN", (f) -> botToken = f);
        env("BOT_COMPANY", (f) -> botCompany = f);
        env("BOT_GIF", (f) -> botGIF = f);
        env("BOT_COLOR", (f) -> botColor = f);
        env("BOT_OWNER_ID", (f) -> botOwnerID = f);
        env("BOT_PREFIX", (f) -> botPrefix = f);
        env("BOT_LEVEL_NAME", (f) -> levelName = f);
        env("BOT_MONEY_NAME", (f) -> moneyName = f);
        env("BOT_MONEY_EMOJI", (f) -> moneyEmoji = f);
        env("BOT_REACTION_ROLE_STRING", (f) -> reactionRoleString = f);
        env("BOT_ROLE_BANISHED", (f) -> roleBanished = f);
        env("BOT_ROLE_MODERATOR", (f) -> roleModerator = f);
        env("BOT_ROLE_ADMINISTRATOR", (f) -> roleAdministrator = f);
        env("BOT_USAGE_MODULES", (f) -> usageModules = f);
        env("BOT_USE_BANISHED_INSTEAD_KICK", (f) -> useBanishedInsteadOfKick = Boolean.parseBoolean(f));
        env("BOT_USE_LINGUA", (f) -> useLingua = Boolean.parseBoolean(f));
        env("BOT_USE_PERSISTENT_ROLES", (f) -> usePersistentRoles = Boolean.parseBoolean(f));
        env("BOT_XP_PER_MESSAGE", (f) -> xpPerMessage = new Range(Float.parseFloat(f.split("_")[0]), Float.parseFloat(f.split("_")[1])));
        env("BOT_XP_MAX_LEVELS", (f) -> xpMaxLevels = Integer.parseInt(f));
        env("BOT_XP_BASE_MULTIPLIER", (f) -> xpBaseMultiplier = Double.parseDouble(f));
        env("BOT_REDIS", (f) -> useRedis = Boolean.parseBoolean(f));
        env("BOT_ROLE_SYSTEM", (f) -> useRoleSystem = Boolean.parseBoolean(f));
        env("BOT_REDIS_ADDRESS", (f) -> redisAddress = f);
        env("BOT_REDIS_PORT", (f) -> redisPort = Integer.parseInt(f));
        env("BOT_REDIS_PASSWORD", (f) -> redisPassword = f);
    }

    // Set from config
    public String botCompany = "VolmitSoftware";
    public String botGIF = "https://images-ext-2.discordapp.net/external/RTML29qcfmg0O2AdcxVRfTo_G8wNRz53le_CGIMyxR8/%3Fsize%3D4096/https/cdn.discordapp.com/avatars/173261518572486656/a_63b6f52a118e915f11bc771985a078c8.gif";
    public String botColor = "0xffff00";
    public String botToken = ""; // LEAVE BLANK FOR TOKEN
    public int dummyPort = 8187;
    public String botOwnerID = "173261518572486656";
    public String botPrefix = ".";
    public String levelName = "Level ";
    public String moneyName = "VolmitCoin";
    public String moneyEmoji = ":coin:";
    public List<String> phishing = Arrays.asList("discordgift.site", "dicsordapp.co" ,"discord.link", "dis.cord", "disc0rd.gg", "discord.gift", "discorcl.click", "gavenitro.com", "discodnitro.info", "discord-airdrop.com", "steamnltros.ru", "discord-app.net", "discord-give.com", "diiscord.com");
    public String redisAddress = "";
    public int redisPort = 0;
    public String redisPassword = "";
    public String reactionRoleString = "**ReactionRoles**";
    public String roleBanished = "Banished";
    public String roleModerator = "Support";
    public String roleAdministrator = "Administrator";
    public String usageModules = "ALL";
    public boolean useRedis = false;
    public boolean useBanishedInsteadOfKick = true; // TODO implement a warning / penalty system
    public boolean useRoleSystem = true;
    public boolean useLingua = true;
    public boolean usePersistentRoles = true;
    public Range xpPerMessage = Range.jitter(0.85f, 0.15f);
    public int xpMaxLevels = 50; // Max roles that can be made by this bot (Level)
    public double xpBaseMultiplier = 2.13d;

    // Set from main class
    public transient Long botID;
    public transient User botUser;
    public transient String botName;

    // Used for hot-loading and config
    private static final FileWatcher fw = new FileWatcher(getFile());
    private static AtomicCache<Kit> instance = new AtomicCache<>();

    public void save() {
        File file = getFile();
        file.getParentFile().mkdirs();
        J.attempt(() -> IO.writeAll(file, new JSONObject(new Gson().toJson(this)).toString(4)));
    }

    public static void tick() {
        if (fw.checkModified()) {
            instance = new AtomicCache<>();
            L.v("Hot-loaded Config");
            Abyss.getJDA();
        }
    }

    public static Kit get() {
        return instance.aquire(() -> {
            File f = getFile();
            System.out.println("Config File location:\n" + f.getAbsolutePath());
            f.getParentFile().mkdirs();
            Kit dummy = new Kit();

            if (!f.exists()) {
                dummy.save();
            }
            try {
                Kit tk = new Gson().fromJson(IO.readAll(f), Kit.class);
                tk.save();
                fw.checkModified();
                return tk;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dummy;
        });
    }

    private static File getFile() {
        return new File("Data/Config.json");
    }

    private void env(String key, Consumer<String> c) {
        String f = System.getenv(key);

        if (f != null) {
            Abyss.info(key + " updated to " + f + " from ENVIRONMENT");
            c.accept(f);
        }
    }
}
