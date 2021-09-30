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

package com.volmit.abyssalith.listeners;

import com.volmit.abyssalith.handlers.MenuHandler;
import com.volmit.abyssalith.handlers.PermHandler;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class RolePageListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String Message = e.getMessage().getContentRaw();
        if (!e.getMessage().getAuthor().isBot() // not a bot
                && e.getMessage().getMentionedRoles().size() > 0 // mentioning roles
                && PermHandler.hasAdmin(Objects.requireNonNull(e.getMember())) // Has admin permissions
                && Message.contains(Kit.get().ReactionRoleString)) { // Contains reaction role string

            if (e.getMessage().getMentionedRoles().size() > 1) {
                MenuHandler.listMenuSend("rolepage",
                        "Choose your Role(s)!",
                        e.getMessage().getMentionedRoles(),
                        e.getChannel());

            } else {
                e.getChannel().sendMessage("CAn you mention more than 1 role please...").queue();
            }
        }
    }
}





