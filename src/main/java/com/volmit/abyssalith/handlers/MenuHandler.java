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
package com.volmit.abyssalith.handlers;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.util.List;


public class MenuHandler { // get and send menu
    public static void RoleListMenu(String id, String PlaceholderText, List<Role> mentionedRoles, MessageChannel messageChannel) {
        sendMenu(genMenuMultipleSelect(id, PlaceholderText, mentionedRoles, messageChannel), messageChannel);
    }

    // return built menu for future modification
    public static SelectionMenu.Builder RoleListMenuGen(String id, String PlaceholderText, List<Role> mentionedRoles, MessageChannel messageChannel) {
        return genMenuMultipleSelect(id, PlaceholderText, mentionedRoles, messageChannel);
    }

    // Get and set menu
    public static void SingleRoleListMenu(String id, String PlaceholderText, List<Role> mentionedRoles, MessageChannel messageChannel) {
        sendMenu(genMenuSingleSelect(id, PlaceholderText, mentionedRoles, messageChannel), messageChannel);
    }

    // return built menu for future modification
    public static SelectionMenu.Builder SingleRoleListMenuGen(String id, String PlaceholderText, List<Role> mentionedRoles, MessageChannel messageChannel) {
        return genMenuSingleSelect(id, PlaceholderText, mentionedRoles, messageChannel);
    }
    // Top one are callables, and instance the lists


    // Bottom are functions for the code
    private static void sendMenu(SelectionMenu.Builder menu, MessageChannel messageChannel) {
        messageChannel.sendMessage("Select the roles that you want!") // Send it to the chat!
                .setActionRow(menu.build())
                .queue(f -> {
                    System.println("Generated Abstracted Menu!");
                });

    }

    private static SelectionMenu.Builder genMenuMultipleSelect(String id, String PlaceholderText, List<Role> mentionedRoles, MessageChannel messageChannel) {
        // This method returns an editable object for chaining purposes
        SelectionMenu.Builder menu = SelectionMenu.create("menu:" + id).setPlaceholder(PlaceholderText);
        int mr = mentionedRoles.size();
        Guild g = mentionedRoles.get(0).getGuild();

        for (Role r : mentionedRoles) {
            if (!g.getEmotesByName(r.getName(), true).isEmpty())
                menu.addOption(r.getName(), r.getId(), Emoji.fromEmote(g.getEmotesByName(r.getName(), true).get(0)));

            if (g.getEmotesByName(r.getName(), true).isEmpty())
                menu.addOption(r.getName(), r.getId());
        }
        menu.setMaxValues(mr);
        return menu;

    }

    private static SelectionMenu.Builder genMenuSingleSelect(String id, String PlaceholderText, List<Role> mentionedRoles, MessageChannel messageChannel) {
        // This method returns an editable object for chaining purposes
        SelectionMenu.Builder menu = SelectionMenu.create("menu:" + id).setPlaceholder(PlaceholderText);
        Guild g = mentionedRoles.get(0).getGuild();

        for (Role r : mentionedRoles) {
            if (!g.getEmotesByName(r.getName(), true).isEmpty())
                menu.addOption(r.getName(), r.getId(), Emoji.fromEmote(g.getEmotesByName(r.getName(), true).get(0)));

            if (g.getEmotesByName(r.getName(), true).isEmpty())
                menu.addOption(r.getName(), r.getId());
        }
        return menu;

    }
}

