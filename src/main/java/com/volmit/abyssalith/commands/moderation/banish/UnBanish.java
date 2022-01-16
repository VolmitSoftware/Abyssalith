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
package com.volmit.abyssalith.commands.moderation.banish;

import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.handlers.BanishHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public class UnBanish extends VolmitCommand {
    // Constructor
    public UnBanish() {
        super(
                "unbanish",
                new String[]{"unbanish", "unbok", "unsilence"},
                new String[]{Kit.get().roleModerator, Kit.get().roleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This command removes the banished role from the person mentioned, or prints them",
                true,
                "mod unbok @psycho"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.info("Un-Applied Banish: " + e.getMessage().getContentRaw());

        if (e.getMessage().getMentionedMembers().size() == 1) {
            Member m = e.getMessage().getMentionedMembers().get(0);
            BanishHandler.unbok(m);
        }
        e.getMessage().delete().queue();

    }
}