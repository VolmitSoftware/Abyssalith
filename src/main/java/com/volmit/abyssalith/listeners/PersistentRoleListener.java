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
import com.volmit.abyssalith.toolbox.Toolkit;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;
import java.util.Set;

public class PersistentRoleListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if (!e.getMember().getUser().isBot() && Toolkit.get().UsePersistentRoles) {
            i("Attempting to reattach roles for: " + e.getMember().getEffectiveName());

            User u = Main.getLoader().getUser(Objects.requireNonNull(e.getMember()).getIdLong()); // Load the user object
            Set<String> lRoles = u.roleIds(); // Load the Roles from the user file
            if (!lRoles.isEmpty() && e.getMember().getRoles().isEmpty()) {
                for (String r : lRoles) {
                    e.getGuild().addRoleToMember(e.getMember().getId(), Objects.requireNonNull(e.getGuild().getRoleById(r))).queue();
                }
                i("Reattached cached roles for: " + e.getMember().getEffectiveName());
            }
            if (lRoles.isEmpty() && !e.getMember().getRoles().isEmpty()) {
                for (Role r : e.getMember().getRoles()) {
                    u.roleIds().add(r.getId());
                    w("Reached Failsafe for role attachment for: " + e.getMember().getEffectiveName() + ", Probably had no roles?");
                }
            }
        }
    }

    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent e) {
        if (!e.getMember().getUser().isBot() && Toolkit.get().UsePersistentRoles) {
            User u = Main.getLoader().getUser(Objects.requireNonNull(e.getMember()).getIdLong()); // Load the user object
            u.roleIds().add(e.getRoles().get(0).getId());
            i("Attached role to : " + e.getMember().getEffectiveName() + "Role ID: " + e.getRoles().get(0).getId());
        }
    }

    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent e) {
        if (!e.getMember().getUser().isBot() && Toolkit.get().UsePersistentRoles) {
            User u = Main.getLoader().getUser(Objects.requireNonNull(e.getMember()).getIdLong()); // Load the user object
            u.roleIds().remove(e.getRoles().get(0).getId());
            i("Removed role from : " + e.getMember().getEffectiveName() + " Role ID: " + e.getRoles().get(0).getId());
        }
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) { // THIS IS A FALLBACK TO KEEP ROLES UPDATED
        User u = Main.getLoader().getUser(Objects.requireNonNull(e.getMember()).getIdLong()); // Load the user object
        Set<String> lRoles = u.roleIds(); // Load the Roles from the user file
        if (lRoles.size() < e.getMember().getRoles().size() && !e.getMember().getUser().isBot() && Toolkit.get().UsePersistentRoles) {
            for (Role r : e.getMember().getRoles()) {
                u.roleIds().add(r.getId());
            }
            i("Found Missing Roles, Rebinding to : " + e.getMember().getEffectiveName());
        }
        if (lRoles.size() > e.getMember().getRoles().size() && !e.getMember().getUser().isBot()) {
            for (Role r : e.getMember().getRoles()) {
                u.roleIds().add(r.getId());
            }
            w("ROLE MISMATCH, REFACTORING ROLES IN USER: " + e.getMember().getEffectiveName());
        }
    }
}
