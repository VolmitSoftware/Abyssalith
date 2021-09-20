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

import art.arcane.quill.format.Form;
import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class MStats extends VolmitCommand {
    // Constructor
    public MStats() {
        super(
                "ping",
                new String[]{"hello", "p"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "This is a debug Ping Keep-alive stats command",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Revealing User Statistics");
        VolmitEmbed embed = new VolmitEmbed(" PONG!", e.getMessage());
        User u = Main.getLoader().getUser(e.getAuthor().getIdLong());
        embed.setDescription("These are your Stats as an example of the return systems enjoy!");
        embed.addField("This is your id!", "`" + u.id() + "`", false);
        embed.addField("This is how broke you are!", "You have: `" + Form.f(u.money()) + "` " + Kit.get().MoneyName, false);
        embed.addField("This is your Experience!", Form.f(u.experience()) + "**xp**", false);
        embed.addField("This are your Stats", "" +
                "Messages sent:" + u.messagesSent()
                + "\n"
                + "Reactions added:" + u.reactions(), false);

        embed.send(e.getMessage(), true, 1000);

    }
}