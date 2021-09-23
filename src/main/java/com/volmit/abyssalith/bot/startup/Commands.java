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
import com.volmit.abyssalith.commands.Eco;
import com.volmit.abyssalith.commands.GStats;
import com.volmit.abyssalith.commands.Links;
import com.volmit.abyssalith.commands.Log;
import com.volmit.abyssalith.commands.MStats;
import com.volmit.abyssalith.commands.Passive;
import com.volmit.abyssalith.commands.Poll;
import com.volmit.abyssalith.commands.RRoles;
import com.volmit.abyssalith.commands.Shutdown;
import com.volmit.abyssalith.commands.Warn;
import com.volmit.abyssalith.listeners.BotListener;
import com.volmit.abyssalith.listeners.LanguageListener;
import com.volmit.abyssalith.listeners.PasteListener;
import com.volmit.abyssalith.listeners.PersistentRoleListener;
import com.volmit.abyssalith.listeners.PhishingListener;
import com.volmit.abyssalith.listeners.UserListener;
import com.volmit.abyssalith.listeners.handlers.ButtonHandler;
import com.volmit.abyssalith.listeners.handlers.MenuHandler;
import com.volmit.abyssalith.listeners.handlers.PasteHandler;
import com.volmit.abyssalith.listeners.handlers.ReactionHandler;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    public static void All(JDA jda) {

        // Log incoming messages
        jda.addEventListener(new Main());
        // Listeners
        jda.addEventListener(new Kit());
        //Hanlers
        jda.addEventListener(new MenuHandler());
        jda.addEventListener(new PasteHandler());
        jda.addEventListener(new ButtonHandler());
        //Listeners
        jda.addEventListener(new BotListener()); // Watches the Bot instance's for stuff
        jda.addEventListener(new LanguageListener());
        jda.addEventListener(new PasteListener());
        jda.addEventListener(new PersistentRoleListener()); // Persistent Roles
        jda.addEventListener(new PhishingListener()); // Persistent Roles
        jda.addEventListener(new ReactionHandler());
        jda.addEventListener(new UserListener()); // Watches the User's instances for stuff

        // Commands
        jda.addEventListener(new Eco()); // Money Subcommand
        jda.addEventListener(new Links());
        jda.addEventListener(new Warn());
        jda.addEventListener(new GStats());
        jda.addEventListener(new MStats());
        jda.addEventListener(new Passive());
        jda.addEventListener(new RRoles());
        jda.addEventListener(new Poll());
        jda.addEventListener(new Log());
        jda.addEventListener(new Shutdown());
        jda.addEventListener(new com.volmit.abyssalith.commands.Commands(jda)); // This one MUST be last
    }
}