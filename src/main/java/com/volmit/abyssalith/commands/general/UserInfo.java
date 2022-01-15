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

import art.arcane.quill.format.Form;
import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public class UserInfo extends VolmitCommand {
    // Constructor
    public UserInfo() {
        super(
                "myinfo",
                new String[]{"myinfo", "myi"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "This command prints out all your info for the server",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.info("Revealing User Statistics");
        VolmitEmbed embed = new VolmitEmbed(" User Info!", e.getMessage());
        User u = Abyss.getLoader().getUser(e.getAuthor().getIdLong());
        String ments = u.recentMentions().toString()
                .replace("{", "")
                .replace("}", "")
                .replace(", ", "\n")
                .replace("0=[", "- [")
                .replace("1=[", "- [")
                .replace("2=[", "- [")
                .replace("3=[", "- [")
                .replace("4=[", "- [");

        embed.setDescription("These are your Stats that are stored in the server for usages, logging, and preventative measures!");
        embed.addField("Your Id", "`" + u.id() + "`", false);
        embed.addField("Bank Balance", "They have: `" + Form.f(u.money()) + "` " + Kit.get().MoneyName, false);
        embed.addField("Your Experience", Form.f(u.experience()) + "**xp**", false);
        embed.addField("Micro Stats", "" + "Messages sent:" + u.messagesSent() + "\n" + "Reactions added:" + u.reactions(), false);
        embed.addField("Warnings Received", u.warnings().size() + " Total Warnings", false);
        embed.addField("Recent Mentions", ments, false);


        embed.send(e.getMessage(), true, 1000);
    }
}