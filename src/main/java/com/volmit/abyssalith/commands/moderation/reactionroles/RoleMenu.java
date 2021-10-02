package com.volmit.abyssalith.commands.moderation.reactionroles;

import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


/*
 *
 *  * Abyssalith is a Discord Bot for Volmit Software's Community
 *  * Copyright (c) 2021 VolmitSoftware (Arcane Arts)
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

public class RoleMenu extends VolmitCommand {
    // Constructor
    public RoleMenu() {
        super(
                "roles",
                new String[]{"roles", "rr"},
                new String[]{Kit.get().RoleModerator,Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "Entering this command gives you a tutorial on how to use the Reaction-Role System.",
                true,
                "rr <subcommand>",
                new VolmitCommand[]{
                        new MentionRoles()

                }
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Reaction Role List Initialized");
        e.getMessage().delete().queue(); // delete the sent message
    }
}