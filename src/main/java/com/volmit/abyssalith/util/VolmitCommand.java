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
package com.volmit.abyssalith.util;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.toolbox.Kit;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;



public class VolmitCommand extends ListenerAdapter {

    @Getter
    public String name;
    @Getter
    public List<String> commands;
    @Getter
    public List<String> roles;
    @Getter
    public String description;
    @Getter
    public boolean needsArguments;
    @Getter
    public String example;
    @Getter
    public String category;
    @Getter
    public List<VolmitCommand> subcommands;


    // Creator sets name, command aliases, requires any one of entered roles, and adds a description and example
    public VolmitCommand(String name, String[] commands, String[] roles, String description, boolean needsArguments, String example) {
        if (commands == null || commands.length == 0) commands = new String[]{name};
        if (roles == null) roles = new String[]{};
        this.name = name;
        this.commands = Arrays.asList(commands);
        this.roles = Arrays.asList(roles);
        this.description = !description.equals("") ? description : "This command has no description";
        this.needsArguments = needsArguments;
        this.example = example;
        this.category = null;
        this.subcommands = null;
    }

    // Creator sets name, command aliases, requires any of entered roles, adds a category description,
    public VolmitCommand(String name, String[] commands, String[] roles, String description, boolean needsArguments, String category, VolmitCommand[] subcommands) {
        if (commands == null || commands.length == 0) commands = new String[]{name};
        if (roles == null) roles = new String[]{};
        this.name = name;
        this.commands = Arrays.asList(commands);
        this.roles = Arrays.asList(roles);
        this.description = !description.equals("") ? description : "This command has no description";
        this.needsArguments = needsArguments;
        this.example = null;
        this.category = category;
        this.subcommands = Arrays.asList(subcommands);
    }

    // Override me!
    public void handle(List<String> args, MessageReceivedEvent e) {
        e.getMessage().reply("The command you ran is improperly written. The handle() must be overwritten.");
    }

    // Handles prefix, handles bot users.
    public void onMessageRecieved(MessageReceivedEvent e) {

        // Prevent bot user
        if (e.getAuthor().isBot()) return;

        // Prevent non-permitted users
        if (noPermission(Objects.requireNonNull(e.getMember()).getRoles(), e.getAuthor().getId())) return;

        // Convert args
        List<String> args = new LinkedList<>(Arrays.asList(e.getMessage().getContentRaw().replace(Kit.get().BotPrefix, "").split(" ")));
        List<String> argc = new LinkedList<>(Arrays.asList(e.getMessage().getContentRaw().split(" ")));
        if (!argc.get(0).contains(Kit.get().BotPrefix)) return; // ignore


        // Check match command
        if (!checkCommand(args.get(0))) return;

        // Print success and continue
        continueToHandle(args, e);
    }

    // Handle
    public void continueToHandle(List<String> args, MessageReceivedEvent e) {

        // Check for permissions (again, but required when passing to here directly)
        if (getRoles() != null && getRoles().size() != 0) {
            if (noPermission(Objects.requireNonNull(e.getMember()).getRoles(), e.getAuthor().getId())) return;
        }
        // Print info message
        Main.info("Command passed checks: " + getName());

        // If it doesn't require arguments just pass it with null
        if (!needsArguments) {
            handle(null, e);
        } else if (getCategory() != null) {
            // If it's a category do:
            if (args.size() < 2) {
                sendCategoryHelp(e.getMessage());
            } else {
                // Print subcommands
                StringBuilder subs = new StringBuilder("Subs: ");
                for (VolmitCommand cmd : getSubcommands()) subs.append(cmd.getName()).append((" "));
                Main.info(subs.toString());
                // Pass to subcommands
                for (VolmitCommand sub : getSubcommands()) {
                    for (String commandAlias : sub.getCommands()) {
                        if (commandAlias.equalsIgnoreCase(args.get(1))) {
                            sub.continueToHandle(args.subList(1, args.size()), e);
                            return;
                        }
                    }
                }
            }
            // Check for arg size to see if help should be sent
        } else if (args.size() < 2) {
            sendHelp(e.getMessage());
            // Pass to (overwritten) handle
        } else {
            Main.info("Final command. Running: " + getName());
            handle(args, e);
        }
    }

    /* Checks if the author has any of the specified roles, or if the ID matches */
    private boolean noPermission(List<Role> roles, String ID) {
        if (getRoles() != null && getRoles().size() != 0) {
            for (Role userRole : roles) {
                String userRoleName = userRole.getName();
                for (String needsRole : getRoles()) {
                    if (needsRole.equals(userRoleName)) {
                        return false;
                    }
                }
                if (ID.equals(userRole.getName())) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /* Checks if the specified command is this command */
    private boolean checkCommand(String command) {
        if (command.equalsIgnoreCase(name)) return true;
        for (String cmd : getCommands()) {
            if (command.equalsIgnoreCase(cmd)) {
                return true;
            }
        }
        return false;
    }


    /* Sends a help message for this command's usage in the specified message's channel */
    public void sendHelp(Message message) {
        VolmitEmbed embed = new VolmitEmbed(Kit.get().BotPrefix + getName() + " Command Usage", message);
        embed.setFooter("All Non-SubCommands are prefaced with the prefix: `" + Kit.get().BotPrefix + "`");
        String cmd = /*Kit.get().BotPrefix +*/ getName().substring(0, 1).toUpperCase() + getName().substring(1);
        if (getCommands().size() < 2) {
            embed.addField(cmd, "`*no aliases*`\n" + getDescription(), true);
        } else {
            embed.addField(
                    cmd,
                    "\n`" + Kit.get().BotPrefix +
                            (getCommands().size() == 2 ?
                                    getCommands().get(1) :
                                    " " + getCommands().subList(1, getCommands().size()).toString()
                                            .replace("[", "").replace("]", "")) +
                            "`\n" + getDescription(),
                    true
            );
        }
        if (getExample() != null) {
            embed.addField("**Usage**", "`" + Kit.get().BotPrefix + getExample() + "`", false);
        }
        if (getRoles() != null && getRoles().size() != 0) {
            embed.addField("**Permitted for role(s)**", "`" + getRoles().toString() + "`", false);
        }
        embed.send(message);
    }

    /* Sends a category help message for this category in the channel of the specified message */
    protected void sendCategoryHelp(Message message) {
        VolmitEmbed embed = new VolmitEmbed(getName().capitalizeWords() + " Command Usage", message);
        String menuName = getName();
        getSubcommands().forEach(command -> {
            String cmd = Kit.get().BotPrefix + menuName + " " + command.getName().substring(0, 1).toUpperCase() + command.getName().substring(1);

            if (command.getCommands().size() < 2) {
                embed.addField(cmd, "`*no aliases*`\n" + command.getDescription(), true);
            } else {
                String body =
                        "\n`" +
                                (command.getCommands().size() == 2 ?
                                        command.getCommands().get(1) :
                                        " " + command.getCommands().subList(1, command.getCommands().size())).toString()
                                        .replaceAbs("[", "").replaceAbs("]", "") +
                                "`\n" +
                                command.getDescription() +
                                (command.getExample() != null ? "\n**usage:**\n`" + Kit.get().BotPrefix + command.getExample() + "`" : "");
                embed.addField(
                        cmd,
                        body,
                        true
                );
            }
        });
        embed.send(message);
    }
}