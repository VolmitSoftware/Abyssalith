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

package com.volmit.abyssalith.listeners.handlers;

import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.util.List;
import java.util.Objects;

public class ReactionHandler extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        try {

            if (!e.getMessage().getAuthor().isBot() && PermHandler.hasAdmin(Objects.requireNonNull(e.getMember())) && e.getMessage().getMentionedRoles().size() > 0) {

                String Message = e.getMessage().lower();
                if (Message.toLowerCase().contains(Kit.get().ReactionRoleString.toLowerCase())) { // Check the descriminator
                    if (e.getMessage().getMentionedRoles().size() > 100) {

                        SelectionMenu.Builder menu = SelectionMenu.create("menu:rolepage").setPlaceholder("Choose your Role(s)!");// shows the placeholder indicating what this menu is for
                        menu.addOption("REMOVE ALL ROLES", "role-remove", Emoji.fromUnicode("\uD83D\uDEAB"));

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
                    }
                }
            }
        } catch (Exception ignored) {
        }// just gets mad when its a bot, im not implementing a fix for this as its redundant
    }


    //TODO, actually get it to work
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        try {

            Message m = e.getChannel().retrieveMessageById(e.getMessageIdLong()).complete();
            String Message = m.getContentRaw().toLowerCase();
            Member mem = e.getGuild().getMember(m.getAuthor());

            if (Message.toLowerCase().contains(Kit.get().ReactionRoleString.toLowerCase())
                    && PermHandler.hasAdmin(Objects.requireNonNull(mem))
                    && m.getMentionedRoles().size() > 0) {

                List<MessageReaction> oReaction = m.getReactions();
                List<Role> oRole = m.getMentionedRoles();

                User oeUser = e.getMember().getUser();
                User authUser = m.getAuthor();

                long oeUserID = oeUser.getIdLong();
                long authUserID = authUser.getIdLong();

                boolean hadRole = false;
                int iter = 0;
                for (MessageReaction reaction : oReaction) {
                    if (reaction.retrieveUsers().complete().contains(m.getAuthor()) && reaction.retrieveUsers().complete().contains(oeUser) && oeUserID != authUserID) {
                        Role mr = oRole.get(iter);

                        for (Role role : e.getMember().getRoles()) {

                            if (oRole.get(iter).getName().equals(role.getName())) {
                                System.out.println("[ALREADY HAVE ROLE, REMOVING]: " + iter);
                                e.getGuild().removeRoleFromMember(e.getUserIdLong(), mr).queue(); // Remove role
                                hadRole = true;
                            }

                        }
                        if (!hadRole) {
                            e.getGuild().addRoleToMember(e.getUserIdLong(), mr).queue(); // Add role
                            System.out.println("[DID NOT HAVE ROLE, ADDING]: " + iter);

                        }
                        reaction.removeReaction(oeUser).queue();

                    } else if (oeUserID == authUserID) {
                        //this is just wrong ^, it will not always be false
                        System.out.println("RoleMaster is adding a role");
                        if (oRole.size() < oReaction.size()) {
                            reaction.removeReaction(oeUser).queue();
                        }

                    } else {
                        reaction.removeReaction(oeUser).queue();
                    }
                    iter++;

                }
            }

        } catch (Exception ignored) {
        } // just gets mad when its a bot, im not implementing a fix for this as its redundant
    }


}

