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
package com.volmit.abyssalith.commands.moderation;

import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.commands.moderation.banish.Banish;
import com.volmit.abyssalith.commands.moderation.banish.UnBanish;
import com.volmit.abyssalith.commands.moderation.warning.AddWarn;
import com.volmit.abyssalith.commands.moderation.warning.ListWarn;
import com.volmit.abyssalith.commands.moderation.warning.PurgeWarns;
import com.volmit.abyssalith.commands.moderation.warning.RemoveWarn;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public class ModHub extends VolmitCommand {
    // Constructor
    public ModHub() {
        super(
                "mod",
                new String[]{"mod", "m", "moderator"},
                new String[]{Kit.get().roleModerator, Kit.get().roleAdministrator}, // Add role name here. Empty: always / 1+: at least one.
                "Moderation Category",
                true,
                "Mod <subcommand>",
                new VolmitCommand[]{
                        new AddWarn(),
                        new ListWarn(),
                        new PurgeWarns(),
                        new RemoveWarn(),
                        new Banish(),
                        new UnBanish()
                }
        );
    }

    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.info("Mod List Initialized");
        e.getMessage().delete().queue(); // delete the sent message
    }

}