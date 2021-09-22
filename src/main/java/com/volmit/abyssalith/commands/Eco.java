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

package com.volmit.abyssalith.commands;

import com.volmit.abyssalith.commands.eco.Get;
import com.volmit.abyssalith.commands.eco.Give;
import com.volmit.abyssalith.commands.eco.Remove;
import com.volmit.abyssalith.commands.eco.Set;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Eco extends VolmitCommand {
    // Constructor
    public Eco() {
        super(
                "eco",
                new String[]{"economy", "eco", "money"},
                new String[]{Kit.get().RoleModerator,Kit.get().RoleAdministrator}, // Add role name here. Empty: always / 1+: at least one.
                "Economy Category",
                true,
                "Eco <subcommand>",
                new VolmitCommand[]{
                        new Give(),
                        new Set(),
                        new Remove(),
                        new Get(),
                }
        );
    }

    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Eco List Initialized");
        e.getMessage().delete().queue(); // delete the sent message
    }

}