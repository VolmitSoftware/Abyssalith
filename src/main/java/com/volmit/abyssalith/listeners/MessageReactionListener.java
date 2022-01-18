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
import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class MessageReactionListener extends ListenerAdapter {
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        Abyss.debug("Reaction Added");
        Message m = e.getChannel().retrieveMessageById(e.getMessageId()).complete();
        if (!e.getUser().isBot()) {
            User u = Abyss.getLoader().getUser(e.getUser().getIdLong());
            u.experience((u.experience() + Kit.get().xpPerMessage.rand()));
            u.reactions(u.reactions() + 1);
        }

        if (!e.getUser().isBot()  // is the reactor a user
                && m.getAuthor().getAvatarUrl().contains("875973161890508830")
                && e.getReaction().toString().contains("U+274c")  // is the X reaction there
                && e.getChannel().retrieveMessageById(e.getMessageId()).complete().getActionRows().size() == 0 /* Are their no clickable actions*/) {
            J.a(() -> {
                Abyss.info(" Cleaning bot response as requested");
                e.getChannel().retrieveMessageById(e.getMessageId()).complete().delete().queue();
            });
        }

    }

    public void onMessageReactionRemove(MessageReactionRemoveEvent e) {
        Abyss.debug("Reaction Removed");
        long uid = e.getUserIdLong();
        if (Abyss.getJDA().getSelfUser().getIdLong() != uid) {
            e.getUserId();
            User u = Abyss.getLoader().getUser(e.getUserIdLong());
            u.reactions(u.reactions() - 1);
            u.experience(u.experience() - Kit.get().xpPerMessage.rand());
        }
    }


}
