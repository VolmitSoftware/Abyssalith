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

package com.volmit.abyssalith.commands.botmaster;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Debug extends VolmitCommand {

    // Constructor
    public Debug() {
        super(
                "debug",
                new String[]{"db"},
                new String[]{},
                "This is a Debug command",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        w("DEBUGGING");
        String oidcheck = e.getMessage().getAuthor().getId();
        if (oidcheck.equals(Kit.get().BotOwnerID)) {
            e.getGuild().createRole().setName("ENGLISH").setMentionable(false).complete();
            i("ENGLISH");
            e.getGuild().createRole().setName("FRENCH").setMentionable(false).complete();
            i("FRENCH");
            e.getGuild().createRole().setName("GERMAN").setMentionable(false).complete();
            i("GERMAN");
            e.getGuild().createRole().setName("SPANISH").setMentionable(false).complete();
            i("SPANISH");
            e.getGuild().createRole().setName("TURKISH").setMentionable(false).complete();
            i("TURKISH");
            e.getGuild().createRole().setName("PORTUGUESE").setMentionable(false).complete();
            i("PORTUGUESE");
            e.getGuild().createRole().setName("POLISH").setMentionable(false).complete();
            i("POLISH");
            e.getGuild().createRole().setName("KOREAN").setMentionable(false).complete();
            i("KOREAN");
            e.getGuild().createRole().setName("JAPANESE").setMentionable(false).complete();
            i("JAPANESE");
            e.getGuild().createRole().setName("DUTCH").setMentionable(false).complete();
            i("DUTCH");
            e.getGuild().createRole().setName("CZECH").setMentionable(false).complete();
            i("CZECH");

        } else {
            e.getChannel().sendMessage("uR noT my DAddY!").queue();
        }
    }
}
