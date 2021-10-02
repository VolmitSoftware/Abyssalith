package com.volmit.abyssalith.commands.moderation.mod.warnings;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.handlers.WarningHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Objects;



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

public class ListWarn extends VolmitCommand {
    // Constructor
    public ListWarn() {
        super(
                "warns",
                new String[]{"warns", "listwarns", "wns"},
                new String[]{Kit.get().RoleModerator, Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This command lists all of the warnings for a user",
                false,
                "mod warns <ID>"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        String[] s = e.getMessage().getContentRaw().split(" ");
        if (e.getMessage().getMentionedMembers().size() == 0 && e.getGuild().getMemberById(s[2].toString()) != null) {
            User u = Main.getLoader().getUser(e.getGuild().getMemberById(s[2].toString()).getIdLong());

            WarningHandler.warnShow(u, e.getChannel());

        }
        e.getMessage().delete().queue();


    }
}