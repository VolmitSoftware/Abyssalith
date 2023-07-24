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


public class Links extends VolmitCommand {
    // Constructor
    public Links() {
        super(
                "links",
                new String[]{"links", "link"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "*Sends useful links (like the wiki)*",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.info("Links Posted");
        VolmitEmbed embed = new VolmitEmbed(" Here you go!", e.getMessage());
        //Commands
        embed.addField("**WIKI LINKS**:", """
                **Adapt:**
                *https://volmitsoftware.gitbook.io/adapt/*
                *https://www.spigotmc.org/resources/adapt-leveling-skills-and-abilities.103790/*
                **Iris:**
                *https://volmitsoftware.gitbook.io/iris/*
                *https://www.spigotmc.org/resources/iris-world-gen-custom-biome-colors.84586/*
                """, false);
        embed.addField("**OTHER LINKS**:", """
                **Patreon:**
                *https://www.patreon.com/volmitsoftware*
                **Discord Links:**
                *discord.gg/volmit*
                *https://canary.discord.com/channels/189665083817852928/770736450558754817/847589978694877194*
                """, false);
        embed.send(e.getMessage());
    }
}