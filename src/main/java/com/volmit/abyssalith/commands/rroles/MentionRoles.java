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
import com.volmit.abyssalith.toolbox.Kit;
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
                new String[]{Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This makes the Mentionable Roles applicable to users", // Description
                false, // Does it use Args
                "rr m" //Example - the prefix
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
                + Kit.get().ReactionRoleString
                + "`, anywhere in the message, AND you need to be an Admin", false);
        embed.addField("Example: ", "" +
                " - " + Kit.get().ReactionRoleString + " - \n"
                + "@role1 @role2  @role3\n"
                + "*__SERVER MUST HAVE CORRESPONDING EMOTES TO NAMES OF ROLES__* ie: if you have a role called SolarFlare, you need a `:SolarFlare:` emote matching the name, This is not required, but it assigns an emoji to a role on use", false);
        embed.send(e.getMessage(), true, 1000);

    }
}
