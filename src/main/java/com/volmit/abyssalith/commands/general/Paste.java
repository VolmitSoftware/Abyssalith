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

import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public class Paste extends VolmitCommand {
    // Constructor
    public Paste() {
        super(
                "paste",
                new String[]{"paste", "plink"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "This command shows the possible paste locations",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.info("Raw Code was Pasted");
        VolmitEmbed embed = new VolmitEmbed("Raw Code Alternative!", e.getMessage());
        embed.setDescription("Generally speaking you can paste your code however you want, however its hard for some of us to read it when we are on our phones, or anything like that, using a paste service will help us help you\n**AND IF YOU PASTE ONE OF THE BOT SUPPORTED LINKS, THE BOT WILL SCAN FOR KNOWN PROBLEMS**");
        embed.addField("Possible Paste Sites!", "" +
                "https://pastebin.com/ `512kb`\n" +
                "https://hastebin.com/ `400kb`\n" +
                "https://gist.github.com/ `100mb`(need GitHub, **Free**)\n" +
                "https://mclo.gs/ `2mb`, Hides IP's/sensitive info, [Bot Supported]\n", false);
        embed.send(e.getMessage());

    }
}