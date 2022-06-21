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


public class Log extends VolmitCommand {
    // Constructor
    public Log() {
        super(
                "log",
                new String[]{"log", "l"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "Gets the log message reply",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.info("Compliance is everything");
        VolmitEmbed embed = new VolmitEmbed("**WHAT IS A LOG?**", e.getMessage());
        embed.setDescription("""
            Hey! This message was sent because we would like to see a log.
            To make sure we get what we need, please follow this with care.
            If you are worried about privacy, you can use `mclo.gs` below to hide IPs etc.
        """);
        embed.addField("*__Why do we ask for Logs__*", """
            **1.** So we can quickly find what the problem is.
            **2.** So we can check which parts are failing.
            **3.** To see Java, Server, and Plugin versions.
            **4.** Other unlisted reasons
        """, false);
        embed.addField("*__What NOT to do__*","""
            **-** Send us a snippet of error codes.
            **-** Send images.
            **-** Removing "unimportant" information.
        """, false);
        embed.addField("How to get a log","""
            **-** The `latest.log` file from your server's `logs` folder.
            **-** Go to <https://pastebin.com/> And paste that file there.
            **-** Alternatively <https://mclo.gs/> and paste it there.
            **-** Or just send the file in chat here.
        """, false);

        //Commands
        //embed.addField("Name Here", "" + "Value here", false);
        embed.send(e.getMessage());

    }
}