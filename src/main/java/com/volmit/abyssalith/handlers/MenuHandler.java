package com.volmit.abyssalith.handlers;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.util.List;

public class MenuHandler { // get and send menu
    public static void RoleListMenu(String id, String PlaceholderText, List<Role> mentionedRoles, TextChannel textChannel) {
        sendMenu(genMenuMultipleSelect(id, PlaceholderText, mentionedRoles, textChannel), textChannel);
    }

    // return built menu for future modification
    public static SelectionMenu.Builder RoleListMenuGen(String id, String PlaceholderText, List<Role> mentionedRoles, TextChannel textChannel) {
        return genMenuMultipleSelect(id, PlaceholderText, mentionedRoles, textChannel);
    }

    // Get and set menu
    public static void SingleRoleListMenu(String id, String PlaceholderText, List<Role> mentionedRoles, TextChannel textChannel) {
        sendMenu(genMenuSingleSelect(id, PlaceholderText, mentionedRoles, textChannel), textChannel);
    }

    // return built menu for future modification
    public static SelectionMenu.Builder SingleRoleListMenuGen(String id, String PlaceholderText, List<Role> mentionedRoles, TextChannel textChannel) {
        return genMenuSingleSelect(id, PlaceholderText, mentionedRoles, textChannel);
    }
    // Top one are callables, and instance the lists


    // Bottom are functions for the code
    private static void sendMenu(SelectionMenu.Builder menu, TextChannel textChannel) {
        textChannel.sendMessage("Select the roles that you want!") // Send it to the chat!
                .setActionRow(menu.build())
                .queue(f -> {
                    System.println("Generated Abstracted Menu!");
                });

    }

    private static SelectionMenu.Builder genMenuMultipleSelect(String id, String PlaceholderText, List<Role> mentionedRoles, TextChannel textChannel) {
        // This method returns an editable object for chaining purposes
        SelectionMenu.Builder menu = SelectionMenu.create("menu:" + id).setPlaceholder(PlaceholderText);
        int mr = mentionedRoles.size();
        Guild g = textChannel.getGuild();

        for (Role r : mentionedRoles) {
            if (!g.getEmotesByName(r.getName(), true).isEmpty())
                menu.addOption(r.getName(), r.getId(), Emoji.fromEmote(g.getEmotesByName(r.getName(), true).get(0)));

            if (g.getEmotesByName(r.getName(), true).isEmpty())
                menu.addOption(r.getName(), r.getId());
        }
        menu.setMaxValues(mr);
        return menu;

    }

    private static SelectionMenu.Builder genMenuSingleSelect(String id, String PlaceholderText, List<Role> mentionedRoles, TextChannel textChannel) {
        // This method returns an editable object for chaining purposes
        SelectionMenu.Builder menu = SelectionMenu.create("menu:" + id).setPlaceholder(PlaceholderText);
        Guild g = textChannel.getGuild();

        for (Role r : mentionedRoles) {
            if (!g.getEmotesByName(r.getName(), true).isEmpty())
                menu.addOption(r.getName(), r.getId(), Emoji.fromEmote(g.getEmotesByName(r.getName(), true).get(0)));

            if (g.getEmotesByName(r.getName(), true).isEmpty())
                menu.addOption(r.getName(), r.getId());
        }
        return menu;

    }
}

