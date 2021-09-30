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

import com.volmit.abyssalith.handlers.PermHandler;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReactionRoleListener extends ListenerAdapter {


    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getMentionedRoles().size() > 0 && PermHandler.hasAdmin(Objects.requireNonNull(e.getMember()))) {
            String Message = e.getMessage().getContentRaw().toLowerCase();
            if (Message.contains(Kit.get().ReactionRoleString.toLowerCase())) { // Check the descriminator
                if (e.getMessage().getMentionedRoles().size() > 4) {

                    SelectionMenu.Builder menu = SelectionMenu.create("menu:rolepage").setPlaceholder("Choose your Role(s)!");// shows the placeholder indicating what this menu is for
                    menu.addOption("REMOVE ALL ROLES", "role-remove-all", Emoji.fromUnicode("\uD83D\uDEAB"));

                    List<Role> oRole = e.getMessage().getMentionedRoles();

                    for (Role role : oRole) { // iterate the roles

                        List<Emote> em = e.getGuild().getEmotesByName(role.getName(), true);
                        Emoji use = null;

                        if (em.size() >= 1) {
                            use = Emoji.fromEmote(em.get(0));
                        } else {
                            use = Emoji.fromUnicode("\uD83E\uDE84");
                        }
                        menu.addOption(role.getName(), role.getId(), "This gives you the role: " + role.getName(), use);
                    }
                    e.getChannel().sendMessage("React to the roles that you want!")
                            .setActionRow(menu.build())
                            .queue(f -> {
                            });

                } else if (e.getMessage().getMentionedRoles().size() <= 4) {
                    List<Button> b = new ArrayList<>();
                    b.add(Button.danger("b-role-remove-all", "🚫Clear🚫"));
                    for (Role r : e.getMessage().getMentionedRoles()) {
                        b.add(Button.secondary(r.getId(), r.getName()));
                    }
                    e.getChannel().sendMessage("Click the roles that you want!").queue(f -> {
                        f.editMessageComponents().setActionRow(b).queue();
                    });

                }
            }
        }
    }

    @Override
    public void onSelectionMenu(SelectionMenuEvent e) { //TODO--------------THIS IS THE MENU MANAGER---------------------//
        boolean replied = false;
        if (Objects.requireNonNull(e.getSelectedOptions()).get(0).getValue().equals("role-remove-all")) {
            Member m = e.getMember();
            List<SelectOption> options = Objects.requireNonNull(e.getComponent()).getOptions();

            for (SelectOption o : options) {
                if (!o.getValue().equalsIgnoreCase("role-remove-all")) {
                    Role roletoremove = (Objects.requireNonNull(e.getGuild())).getRoleById(o.getValue());
                    if (roletoremove != null && m != null) {
                        e.getGuild().removeRoleFromMember(m, roletoremove).queue();
                    }
                }

            }
            e.reply("Removed ALL ROLES from this list!").setEphemeral(true).queue();
            replied = true;

        }
        List<Role> roles = Objects.requireNonNull(e.getGuild()).getRoles();  // ALL OF THE GUILDS ROLES
        String menuID = Objects.requireNonNull(e.getSelectedOptions()).get(0).getValue();  // GUILD SELECTION ROLES
        for (Role r : roles) {  //r == Role    LOOP THROUGH THE SERVER ROLES
            if (menuID.equals(r.getId())) { //IF the menue option that was clicked = the ROLE's ID'
                if (Objects.requireNonNull(e.getMember()).getRoles().contains(r)) {
                    e.reply("Removed the role: " + r.getName() + " from you!").setEphemeral(true).queue();
                    e.getGuild().removeRoleFromMember(Objects.requireNonNull(e.getGuild().getMemberById(e.getUser().getId())), r).queue();
                    replied = true;

                } else if (!e.getMember().getRoles().contains(r)) {
                    e.getGuild().addRoleToMember(Objects.requireNonNull(e.getGuild().getMemberById(e.getUser().getId())), r).queue();
                    e.reply("Added the role: " + r.getName() + " to you!").setEphemeral(true).queue();
                    replied = true;
                }
            }
        }
        if (!replied)
            e.reply("hElp! call my owner! i had a fuckie wuckie").setEphemeral(true).queue(f -> {
            });
    }


    public void onButtonClick(ButtonClickEvent e) {

        boolean replied = false;
        if (Objects.requireNonNull(e.getButton().getId()).equals("b-role-remove-all")) {
            Member m = e.getMember();
            List<Button> options = e.getMessage().getButtons();

            for (Button o : options) {
                if (!o.getId().equalsIgnoreCase("b-role-remove-all")) {
                    Role roletoremove = (Objects.requireNonNull(e.getGuild())).getRoleById(o.getId());
                    if (roletoremove != null && m != null) {
                        e.getGuild().removeRoleFromMember(m, roletoremove).queue();
                    }
                }

            }
            replied = true;
            e.reply("Roles Cleared!").setEphemeral(true).queue();
        }
        try {
            List<Role> roles = Objects.requireNonNull(e.getGuild()).getRoles();  // ALL OF THE GUILDS ROLES
            String mrolename = e.getButton().getLabel().toLowerCase();  // GUILD SELECTION ROLE
            String menuID = e.getGuild().getRolesByName(mrolename, true).get(0).getId();  // GUILD SELECTION ROLE
            for (Role r : roles) {  //r == Role    LOOP THROUGH THE SERVER ROLES
                if (menuID.equals(r.getId())) { //IF the menue option that was clicked = the ROLE's ID'

                    if (Objects.requireNonNull(e.getMember()).getRoles().contains(r)) {
                        e.reply("Removed the role: " + r.getName() + " from you!").setEphemeral(true).queue();
                        e.getGuild().removeRoleFromMember(Objects.requireNonNull(e.getGuild().getMemberById(e.getUser().getId())), r).queue();
                        replied = true;

                    } else if (!e.getMember().getRoles().contains(r)) {
                        e.getGuild().addRoleToMember(Objects.requireNonNull(e.getGuild().getMemberById(e.getUser().getId())), r).queue();
                        e.reply("Added the role: " + r.getName() + " to you!").setEphemeral(true).queue();
                        replied = true;
                    }
                }
            }
            if (!replied)
                e.reply("hElp! call my owner! i had a fuckie wuckie").setEphemeral(true).queue(f -> {
                });
        } catch (Exception ignored) {
        }
    }
}





