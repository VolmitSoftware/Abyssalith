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
import com.volmit.abyssalith.Main;
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
        env("BOT_DUMMY_PORT", (f) -> DummyPort = Integer.parseInt(f));
        env("BOT_TOKEN", (f) -> BotToken = f);
        env("BOT_COMPANY", (f) -> BotCompany = f);
        env("BOT_GIF", (f) -> BotGIF = f);
        env("BOT_COLOR", (f) -> BotColor = f);
        env("BOT_OWNER_ID", (f) -> BotOwnerID = f);
        env("BOT_PREFIX", (f) -> BotPrefix = f);
        env("BOT_LEVEL_NAME", (f) -> LevelName = f);
        env("BOT_MONEY_NAME", (f) -> MoneyName = f);
        env("BOT_MONEY_EMOJI", (f) -> MoneyEmoji = f);
        env("BOT_REACTION_ROLE_STRING", (f) -> ReactionRoleString = f);
        env("BOT_ROLE_BANISHED", (f) -> RoleBanished = f);
        env("BOT_ROLE_MODERATOR", (f) -> RoleModerator = f);
        env("BOT_ROLE_ADMINISTRATOR", (f) -> RoleAdministrator = f);
        env("BOT_USE_BANISHED_INSTEAD_KICK", (f) -> UseBanishedInsteadOfKick = Boolean.parseBoolean(f));
        env("BOT_USE_LINGUA", (f) -> UseLingua = Boolean.parseBoolean(f));
        env("BOT_USE_PERSISTENT_ROLES", (f) -> UsePersistentRoles = Boolean.parseBoolean(f));
        env("BOT_XP_PER_MESSAGE", (f) -> XpPerMessage = new Range(Float.parseFloat(f.splitAbs("_")[0]), Float.parseFloat(f.splitAbs("_")[1])));
        env("BOT_XP_MAX_LEVELS", (f) -> XpMaxLevels = Integer.parseInt(f));
        env("BOT_XP_BASE_MULTIPLIER", (f) -> XpBaseMultiplier = Double.parseDouble(f));
        env("BOT_REDIS", (f) -> UseRedis = Boolean.parseBoolean(f));
        env("BOT_REDIS_ADDRESS", (f) -> RedisAddress = f);
        env("BOT_REDIS_PORT", (f) -> RedisPort = Integer.parseInt(f));
        env("BOT_REDIS_PASSWORD", (f) -> RedisPassword = f);
    }

    // Set from config

    public String BotCompany = "VolmitSoftware";
    public String BotGIF = "https://images-ext-2.discordapp.net/external/RTML29qcfmg0O2AdcxVRfTo_G8wNRz53le_CGIMyxR8/%3Fsize%3D4096/https/cdn.discordapp.com/avatars/173261518572486656/a_63b6f52a118e915f11bc771985a078c8.gif";
    public String BotColor = "0xffff00";
    public String BotToken = ""; // LEAVE BLANK FOR TOKEN
    public int DummyPort = 8187;
    public String BotOwnerID = "173261518572486656";
    public String BotPrefix = ".";
    public String LevelName = "Level ";
    public String MoneyName = "VolmCoin";
    public String MoneyEmoji = ":coin:";
    public List<String> Phishing = Arrays.asList("discordgift.site", "discord.link", "dis.cord", "disc0rd.gg", "discord.gift", "discorcl.click", "gavenitro.com", "discodnitro.info", "discord-airdrop.com", "steamnltros.ru", "discord-app.net", "discord-give.com", "diiscord.com");
    public String RedisAddress = "";
    public int RedisPort = 0;
    public String RedisPassword = "";
    public String ReactionRoleString = "**ReactionRoles**";
    public String RoleBanished = "Banished";
    public String RoleModerator = "Support";
    public String RoleAdministrator = "Administrator";
    public boolean UseRedis = false;
    public boolean UseBanishedInsteadOfKick = true; // TODO implement a warning / penalty system
    public boolean UseLingua = true;
    public boolean UsePersistentRoles = true;
    public Range XpPerMessage = Range.jitter(0.85f, 0.15f);
    public int XpMaxLevels = 50; // Max roles that can be made by this bot (Level)
    public double XpBaseMultiplier = 2.13d;

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
            Main.getJDA();
        }
    }

    public static Kit get() {
        return instance.aquire(() -> {
            File f = getFile();
            System.out.println(f.getAbsolutePath());
            f.getParentFile().mkdirs();
            Kit dummy = new Kit();

            if (!f.exists()) {
                dummy.save();
            }
            try {
                Kit tk = new Gson().fromJson(IO.readAll(f), Kit.class);
                System.out.println(tk.BotToken);
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
            d(key + " updated to " + f + " from ENVIRONMENT");
            c.accept(f);
        }
    }
}
