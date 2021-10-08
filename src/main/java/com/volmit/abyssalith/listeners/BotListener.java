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
package com.volmit.abyssalith.listeners;

import art.arcane.quill.execution.J;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class BotListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().imUser()
                && !e.getMessage().getEmbeds().isEmpty()
                && e.getMessage().getActionRows().size() == 0 // Are their no clickable actions
        ) {
            e.getMessage().addReaction("U+274C").queue();
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot()  // is the reactor a user
                && e.getMessage().getAuthor().isBot()  // is author a bot
                && e.getReaction().toString().contains("U+274c")  // is the X reaction there
                && e.getMessage().getActionRows().size() == 0 // Are their no clickable actions

        ) {

            J.a(() -> {
                i(" Cleaning bot response as requested");
                e.getMessage().delete().queue();
            });
        }
    }
}


