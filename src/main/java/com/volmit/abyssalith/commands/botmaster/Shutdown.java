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

import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public class Shutdown extends VolmitCommand {

    // Constructor
    public Shutdown() {
        super(
                "stop",
                new String[]{"stop", "kill", "s"},
                new String[]{Kit.get().RoleModerator, Kit.get().RoleAdministrator},
                "Stops the Bot boi",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.warn("Terminating the Bot");
        String oidcheck = e.getMessage().getAuthor().getId();
        if (oidcheck.equals(Kit.get().BotOwnerID)) {
            Abyss.warn("KILLING BOT");
            e.getMessage().delete().queue();
            Abyss.shutdown();
        } else {
            e.getChannel().sendMessage("uR noT my DAddY!").queue();
        }
    }
}
