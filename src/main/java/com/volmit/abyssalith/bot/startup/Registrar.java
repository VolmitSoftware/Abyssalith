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

import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.commands.general.*;
import com.volmit.abyssalith.commands.moderation.EcoHub;
import com.volmit.abyssalith.commands.moderation.ModHub;
import com.volmit.abyssalith.commands.moderation.reactionroles.RoleMenu;
import com.volmit.abyssalith.listeners.*;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Registrar extends ListenerAdapter {

    public static void All(JDA jda) {

        //Listeners
        jda.addEventListener(new Abyss()); // [ DONT TOUCH THESE TWO LISTENERS ]
        jda.addEventListener(new Kit());  // [ DONT TOUCH THESE TWO LISTENERS ]

        jda.addEventListener(new ButtonListener());
        jda.addEventListener(new PasteListener());
        jda.addEventListener(new GuildListener()); // Persistent Roles
        jda.addEventListener(new SelectionMenuListener());
        jda.addEventListener(new MessageReactionListener()); // Watches the User's instances for stuff
        jda.addEventListener(new MessageReceivedListener()); // Watches the User's messages for stuff

        // Commands
        jda.addEventListener(new EcoHub()); // Money Subcommand
        jda.addEventListener(new ModHub()); // Money Subcommand
        jda.addEventListener(new Links());
        jda.addEventListener(new Paste());
        jda.addEventListener(new ServerInfo());
        jda.addEventListener(new UserInfo());
        jda.addEventListener(new Passive());
        jda.addEventListener(new RoleMenu());
        jda.addEventListener(new Log());

        jda.addEventListener(new com.volmit.abyssalith.commands.general.Commands(jda)); // This one MUST be last
    }
}
