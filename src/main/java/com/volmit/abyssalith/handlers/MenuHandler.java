package com.volmit.abyssalith.handlers;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.util.List;

public class MenuHandler {
    public static void listMenuSend(String id, String PlaceholderText, List<Role> mentionedRoles, TextChannel textChannel) {
        SelectionMenu.Builder menu = SelectionMenu.create("menu:" + id).setPlaceholder(PlaceholderText);
        int mr = mentionedRoles.size();
        menu.setMaxValues(mr);

        Guild g = textChannel.getGuild();

        for (Role mrole : mentionedRoles) {
            if (!g.getEmotesByName(mrole.getName(), true).isEmpty())
                menu.addOption(mrole.getName(), mrole.getId(), Emoji.fromEmote(g.getEmotesByName(mrole.getName(), true).get(0)));

            if (g.getEmotesByName(mrole.getName(), true).isEmpty())
                menu.addOption(mrole.getName(), mrole.getId());
        }

        textChannel.sendMessage("Select the roles that you want!")
                .setActionRow(menu.build())
                .queue(f -> {
                    System.println("Generated Abstracted Menu!");
                });
    }

    public static SelectionMenu.Builder listMenu(String id, String PlaceholderText, List<Role> mentionedRoles, TextChannel textChannel) {
        SelectionMenu.Builder menu = SelectionMenu.create("menu:" + id).setPlaceholder(PlaceholderText);
        int mr = mentionedRoles.size();
        Guild g = textChannel.getGuild();

        for (Role mrole : mentionedRoles) {
            if (!g.getEmotesByName(mrole.getName(), true).isEmpty())
                menu.addOption(mrole.getName(), mrole.getId(), Emoji.fromEmote(g.getEmotesByName(mrole.getName(), true).get(0)));

            if (g.getEmotesByName(mrole.getName(), true).isEmpty())
                menu.addOption(mrole.getName(), mrole.getId());
        }
        menu.setMaxValues(mr);
        return menu;
    }
}


//    SelectionMenu.Builder menu = SelectionMenu.create("menu:rolepage").setPlaceholder("Choose your Role(s)!");// shows the placeholder indicating what this menu is for
//                    menu.addOption("REMOVE ALL ROLES", "role-remove-all", Emoji.fromUnicode("\uD83D\uDEAB"));
//
//                            List<Role> oRole = e.getMessage().getMentionedRoles();
//        int mr = oRole.size();
//
//        for (Role role : oRole) { // iterate the roles
//
//        List<Emote> em = e.getGuild().getEmotesByName(role.getName(), true);
//        Emoji use = null;
//
//        if (em.size() >= 1) {
//        use = Emoji.fromEmote(em.get(0));
//        } else {
//        use = Emoji.fromUnicode("\uD83E\uDE84");
//        }
//        menu.addOption(role.getName(), role.getId(), "This gives you the role: " + role.getName(), use);
//        }
//        menu.setRequiredRange(0, mr);
//        e.getChannel().sendMessage("Select the roles that you want!")
//        .setActionRow(menu.build())
//        .queue(f -> {
//        });
