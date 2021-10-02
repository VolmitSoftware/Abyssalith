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

package com.volmit.abyssalith.bot.startup;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.commands.moderation.mod.EcoHub;
import com.volmit.abyssalith.commands.general.Links;
import com.volmit.abyssalith.commands.general.Log;
import com.volmit.abyssalith.commands.general.Passive;
import com.volmit.abyssalith.commands.general.Paste;
import com.volmit.abyssalith.commands.moderation.mod.ModHub;
import com.volmit.abyssalith.commands.moderation.reactionroles.RoleMenu;
import com.volmit.abyssalith.commands.general.ServerInfo;
import com.volmit.abyssalith.commands.general.UserInfo;
import com.volmit.abyssalith.listeners.BotListener;
import com.volmit.abyssalith.listeners.ButtonListener;
import com.volmit.abyssalith.listeners.LanguageListener;
import com.volmit.abyssalith.listeners.PasteLinkListener;
import com.volmit.abyssalith.listeners.PasteListener;
import com.volmit.abyssalith.listeners.PersistentRoleListener;
import com.volmit.abyssalith.listeners.PhishingListener;
import com.volmit.abyssalith.listeners.RolePageListener;
import com.volmit.abyssalith.listeners.SelectionMenuListener;
import com.volmit.abyssalith.listeners.MentionListener;
import com.volmit.abyssalith.listeners.UserListener;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Registrar extends ListenerAdapter {

    public static void All(JDA jda) {
        //https://github.com/DV8FromTheWorld/JDA/releases/tag/v4.3.0 REMINDER FOR LATER TO ADD ALL AS SLASH COMMANDS

//        jda.addEventListener(new Debug());
//        jda.addEventListener(new Shutdown());
        

        //Listeners
        jda.addEventListener(new Main()); // [ DONT TOUCH THESE TWO LISTENERS ]
        jda.addEventListener(new Kit());  // [ DONT TOUCH THESE TWO LISTENERS ]
        jda.addEventListener(new BotListener()); // Watches the Bot instance's for stuff
        jda.addEventListener(new ButtonListener());
        jda.addEventListener(new PasteListener());
        jda.addEventListener(new LanguageListener());
        jda.addEventListener(new PasteLinkListener());
        jda.addEventListener(new PersistentRoleListener()); // Persistent Roles
        jda.addEventListener(new PhishingListener()); // Persistent Roles
        jda.addEventListener(new RolePageListener());
        jda.addEventListener(new SelectionMenuListener());
        jda.addEventListener(new MentionListener());
        jda.addEventListener(new UserListener()); // Watches the User's instances for stuff

        // Commands
        jda.addEventListener(new EcoHub()); // Money Subcommand
        jda.addEventListener(new ModHub()); // Money Subcommand
        jda.addEventListener(new Links());
        jda.addEventListener(new Paste());
        jda.addEventListener(new ServerInfo());
        jda.addEventListener(new UserInfo());
        jda.addEventListener(new Passive());
        jda.addEventListener(new RoleMenu());
        //jda.addEventListener(new Poll()); BROKEN
        jda.addEventListener(new Log());
        jda.addEventListener(new com.volmit.abyssalith.commands.general.Commands(jda)); // This one MUST be last
    }
}
