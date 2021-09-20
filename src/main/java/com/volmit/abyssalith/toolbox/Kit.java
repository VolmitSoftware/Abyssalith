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

public class Kit extends ListenerAdapter {
    // Set from config
    public String BotCompany = "NextdoorSoftworks";
    public String BotGIF = "https://images-ext-2.discordapp.net/external/RTML29qcfmg0O2AdcxVRfTo_G8wNRz53le_CGIMyxR8/%3Fsize%3D4096/https/cdn.discordapp.com/avatars/173261518572486656/a_63b6f52a118e915f11bc771985a078c8.gif";
    public String BotColor = "0xffff00";
    public String BotToken = ""; // LEAVE BLANK FOR TOKEN
    public String BotOwnerID = "173261518572486656";
    public String BotPrefix = ".";
    public String LevelName = "Level ";
    public String MoneyName = "VolmCoin";
    public String MoneyEmoji = ":coin:";
    public String ReactionRoleString = "**ReactionRoles**";
    public String RoleBanished = "Banished";
    public String RoleModerator = "Support";
    public String RoleAdministrator = "Administrator";
    public boolean UseBanishedInsteadOfKick = true; // TODO implement a warning / penalty system
    public boolean UseLingua = true;
    public boolean UsePersistentRoles = true;
    public Range XpPerMessage = Range.jitter(0.9f, 0.2f);
    public int XpMaxLevels = 50; // Max roles that can be made by this bot (Level)
    public double XpBaseMultiplier = 1.25f;

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
        return new File("config/MainConfig.json");
    }

    public void envInject() {
        String f = System.getenv("TOKEN");

        if(f != null)
        {
            d("Token updated to " + f + " from ENVIRONMENT");
            BotToken = f;
            save();
        }
    }
}
