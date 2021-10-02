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

package com.volmit.abyssalith.commands.general;

import com.volmit.abyssalith.handlers.WarningHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import static art.arcane.amulet.MagicalSugar.*;

import java.util.Arrays;
import java.util.List;

public class Warn extends VolmitCommand {
    // Constructor
    public Warn() {
        super(
                "warn",
                new String[]{"warn", "strike", "wn"},
                new String[]{Kit.get().RoleModerator, Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This command Applies Warnings to the person mentioned, or prints them",
                true,
                "warn @psycho being a bad developer\n or \n.warn show <userid>>"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("MESSAGE: "+ e.getMessage());
        List<String> warningMsg = new java.util.ArrayList<>(List.of(""));
        int argcnt = args.size()-1;

        if(args.get(1).contains("@") && e.getMessage().getMentionedMembers().size() > 0){
            Member m = e.getMessage().getMentionedMembers().get(0);
            WarningHandler.warn(m, e);

            for(Integer s : 2 to argcnt){
                warningMsg.add(args[s]);
            }

        }





    }
}