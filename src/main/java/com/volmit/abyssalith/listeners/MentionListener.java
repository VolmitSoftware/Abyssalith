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

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;


public class MentionListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getMessage().getMentionedMembers().size() > 0) {
            for (Member m : e.getMessage().getMentionedMembers()) {
                User u = Main.getLoader().getUser(m.getIdLong());
                if (u.recentMentions().size() > 2) {
                    u.recentMentions().remove(u.recentMentions().size() - 1);
                }
                u.recentMentions().put(u.recentMentions().size(), "[**USER**]" + Objects.requireNonNull(e.getMember()).getEffectiveName() + " [**SAID**]: " + e.getMessage().getContentRaw());
            }
        }
    }
}
