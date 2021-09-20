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

package com.volmit.abyssalith.commands.rroles;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.toolbox.Toolkit;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class MentionRoles extends VolmitCommand {
    // Constructor
    public MentionRoles() {
        super(
                "mentionroles", //Name
                new String[]{"mr", "m"}, //Alias's
                new String[]{Toolkit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This makes the Mentionable Roles applicable to users", // Description
                true, // Does it use Args
                "eco get @Psycho" //Example - the prefix
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Mention Roles List Initialized");
        VolmitEmbed embed = new VolmitEmbed(" REACTION ROLES!!", e.getMessage());
        User u = Main.getLoader().getUser(e.getAuthor().getIdLong());
        embed.setDescription("*This should explain how to use the reaction roles system, should you have any questions!*");
        embed.addField("Create a Reaction-able Message!", " First of all your message needs to contain the following: `"
                + Toolkit.get().ReactionRoleString
                + "`, anywhere in the message, AND you need to be able to view audit log (for now)", false);
        embed.addField("Example: ", "" +
                " - " + Toolkit.get().ReactionRoleString + " - \n"
                + ":coin: for @role1 or :eyes: @role2 even :hearts: for @role3\n"
                + "*__Supports Server Emotes__*", false);
        embed.addField("Adding the reactions!", "Finally the author must react to the message with your reactions (*in order of mentioned roles*) to assign them eg: [:coin: :eyes: :hearts:] ", false);
        embed.addField("*Notes*", "*This will remove any reactions that are NOT from the Message Author (prevents spam) Unless the rolemaster adds TOO MANY roles, it will clear the reactions for you to react once more*", false);
        embed.send(e.getMessage(), true, 1000);

    }
}
