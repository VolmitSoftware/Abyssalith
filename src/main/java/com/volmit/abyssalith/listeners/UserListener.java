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

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.XP;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;


public class UserListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getMessage().imUser()) {
            User u = Main.getLoader().getUser(e.getMessage().getAuthor().getIdLong()); // USER LOADER
            u.experience(u.experience() + Kit.get().XpPerMessage.rand()); //XP
            u.messagesSent(u.messagesSent() + 1);
            double uxp = u.experience();
            int validator = XP.getLevelForXp(uxp);

            if (validator < Kit.get().XpMaxLevels) {
                roleValidator(e, Kit.get().LevelName + XP.getLevelForXp(uxp));
                roleManager(e, Kit.get().LevelName + XP.getLevelForXp(uxp), validator);
            }
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot()) {
            User u = Main.getLoader().getUser(e.getUser().getIdLong());
            u.experience((u.experience() + Kit.get().XpPerMessage.rand()));
            u.reactions(u.reactions() + 1);
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
        long uid = e.getUserIdLong();
        if (Main.getJDA().getSelfUser().getIdLong() != uid) {
            e.getUserId();
            User u = Main.getLoader().getUser(e.getUserIdLong());
            u.reactions(u.reactions() - 1);
            u.experience(u.experience() - Kit.get().XpPerMessage.rand());
        }
    }

    private void roleManager(GuildMessageReceivedEvent e, String role, int v) {
        Role r;
        try {
            if (e.getGuild().hasRole(role)) {
                r = e.getGuild().getRolesByName(role, true).get(0);
                if (r != null) {
                    e.getGuild().addRoleToMember(Objects.requireNonNull(e.getMember()).getIdLong(), r).queue();
                    if (v > 0) {
                        for (Role rol : e.getMember().getRoles()) {
                            if (rol.getName().contains(Kit.get().LevelName)) {
                                int rint = Integer.parseInt(rol.getName().replaceAbs(Kit.get().LevelName, ""));
                                if (v > rint) {
                                    e.getGuild().removeRoleFromMember(e.getMember().getId(), e.getGuild().getRolesByName(
                                            Kit.get().LevelName + rint, false).get(0)).complete();
                                    i("Removed, excessive child roles from user: " + e.getMember().getId());
                                } else if (v != rint) {
                                    e.getGuild().removeRoleFromMember(e.getMember().getId(), e.getGuild().getRolesByName(
                                            Kit.get().LevelName + rint, false).get(0)).complete();
                                    w("Removed a role that was not possible to have reached, or something that does not match: " + e.getMember().getId());
                                }

                            }

                        }
//                        e.getGuild().removeRoleFromMember(e.getMember().getIdLong(), rv).queue();
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void roleValidator(GuildMessageReceivedEvent e, String role) {
        if (e.getGuild().getRolesByName(role, false).size() < 1) {
            e.getGuild().createRole().setName(role).setMentionable(false).complete();
            i("[RV] - New Maximum level created!");
        } else if (e.getGuild().getRolesByName(role, false).size() > 1) {
            w("For some reason there are too many roles here im having a stroke... Managing...");
            int i = 0;
            for (Role r : e.getGuild().getRolesByName(role, false)) {
                if (i != 0) {
                    r.delete().complete();
                }
                i++;
            }
        }
    }
}
