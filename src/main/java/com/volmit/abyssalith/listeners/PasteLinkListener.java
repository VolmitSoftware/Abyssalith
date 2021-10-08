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

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;


public class PasteLinkListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().contains("https://pastebin.com/")) {
            i("Started the Paste Service");
            String str = e.getMessage().getContentRaw();
            String[] pbArr = str.split(" ");
            for (String s : pbArr) {
                if (s.contains("https://pastebin.com/")) {
                    e.getChannel().sendMessage("Would you like me to scan this for you?: <" + s + ">").queue(f -> {
                        f.editMessageComponents().setActionRow(
                                Button.success("pastbinlinknew", "Yes please!"),
                                Button.danger("no", "No, go away!")
                        ).queue();
                        i("Sent Paste Service Buttons");
                    });
                }
            }
        }
        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().contains("https://mclo.gs/")) {
            i("Started the McLogs Service");
            String str = e.getMessage().getContentRaw();
            String[] pbArr = str.split(" ");
            for (String s : pbArr) {
                if (s.contains("https://mclo.gs/")) {
                    e.getChannel().sendMessage("Would you like me to scan this for you?: <" + s + ">").queue(f -> {
                        f.editMessageComponents().setActionRow(
                                Button.success("mcloglinknew", "Yes please!"),
                                Button.danger("no", "No, go away!")
                        ).queue();
                        i("Sent McLog Service Buttons");
                    });
                }
            }
        }
        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().contains("https://hastebin.com/")) {
            i("Started the Hastebin Service");
            String str = e.getMessage().getContentRaw();
            String[] pbArr = str.split(" ");
            for (String s : pbArr) {
                if (s.contains("https://hastebin.com/")) {
                    e.getChannel().sendMessage("Would you like me to scan this for you?: <" + s + ">").queue(f -> {
                        f.editMessageComponents().setActionRow(
                                Button.success("hastebinlinknew", "Yes please!"),
                                Button.danger("no", "No, go away!")
                        ).queue();
                        i("Sent HasteBin Service Buttons");
                    });
                }
            }
        }
    }

}
