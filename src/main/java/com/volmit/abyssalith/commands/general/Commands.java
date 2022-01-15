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
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;


public class Commands extends VolmitCommand {

    // Commands stored
    private VolmitCommand[] botCommands = null;

    // Constructor
    public Commands(JDA jda) {
        super(
                "Commands",
                new String[]{"commands", "?", "help"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "Sends the command help page (this one) If you would like to see the Passives that this bot does you can type `" + Kit.get().BotPrefix + "passive` and see what this bot does Passively",
                false,
                null
        );
        setCommands(processCMDs(jda));
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.info("Command List Initialized");

        // Init embed
        VolmitEmbed embed = new VolmitEmbed("The Abyssalith - " + Kit.get().botName + " Info Page!", e.getMessage());

        // Add explanation
        embed.addField(
                "All commands you can use",
                Kit.get().BotPrefix + "<command> for more help on the command",
                true
        );

        // Loop over and add all commands with their respective information
        for (VolmitCommand command : botCommands) {
            String cmd = /*Kit.get().BotPrefix + */ command.getName().substring(0, 1).toUpperCase() + command.getName().substring(1);
            embed.setFooter("All Non-SubCommands are prefaced with the prefix: `" + Kit.get().BotPrefix + "`");
            if (command.getCommands().size() < 2) {
                embed.addField(cmd, "`*no aliases*`\n" + command.getDescription(), false);
            } else {
                StringBuilder body = new StringBuilder();
                body
                        .append("\n`")
                        .append(Kit.get().BotPrefix)
                        .append(
                                command.getCommands().size() == 2 ?
                                        command.getCommands().get(1) :
                                        "" + command.getCommands().subList(1, command.getCommands().size()).toString()
                                                .replace("[", "").replace("]", "")
                        )
                        .append("`\n")
                        .append(command.getDescription())
                        .append(command.getExample() != null ? "\n**Usage**\n" + command.getExample() : "");
                if (command.getRoles() != null && command.getRoles().size() != 0) {
                    if (command.getRoles().size() == 1) {
                        body.append("\n__Required:__ ").append(command.getRoles().get(0));
                    } else {
                        body.append("\n__Required:__ ").append(command.getRoles().toString()
                                .replace("[", "").replace("]", ""));
                    }
                }
                embed.addField(
                        cmd,
                        body.toString(),
                        false
                );
            }
        }

        // Send the embed
        embed.send(e.getMessage(), true, 1000);
    }

    /// Other functions
    // Sets the commands
    public void setCommands(List<VolmitCommand> commands) {
        botCommands = commands.toArray(new VolmitCommand[0]);
    }

    // Gets all listeners of the specified JDA
    public List<VolmitCommand> processCMDs(JDA jda) {
        List<VolmitCommand> foundCommands = new ArrayList<>();
        jda.getRegisteredListeners().forEach(c -> {

            if (c instanceof VolmitCommand && c.getClass().getPackageName().contains(".commands")) {
                foundCommands.add((VolmitCommand) c);
            }
        });
        foundCommands.add(this);
        return foundCommands;
    }
}
