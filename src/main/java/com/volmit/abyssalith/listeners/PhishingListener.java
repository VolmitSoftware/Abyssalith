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

import com.volmit.abyssalith.handlers.WarningHandler;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;


public class PhishingListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e) {
        for (String p : Kit.get().Phishing) {
            if (e.getMessage().lower().contains(p)) {
                e.getMessage().delete().queue();
                Objects.requireNonNull(e.getMember()).getUser().openPrivateChannel().complete().sendMessage("Hello, You have been banned from volmit for sending Phishing links: `\n" +
                        e.getMessage() +
                        "`\nIf you feel this is a mistake, please send a friend request to: `⋈-NestorPsycho-⋈#0001` and explain the problem, otherwise your account was compromised, or you were unaware of what you were sending \n" +
                        "regardless, you have been banned, until you either fix your account, or handle the situation").queue();
                try {
                    WarningHandler.phishBan(e.getMember(), e.getGuild(), e.getMessage()); // ez ban
                } catch (Exception ignored) {
                } // Don't care about exceptions here, the only possible one is a permission one, and the bot needs to be able to handle it elsewhere
            }
        }
    }
}