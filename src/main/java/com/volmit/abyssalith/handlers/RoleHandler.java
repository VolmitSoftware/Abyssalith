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

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;


public class RoleHandler {
    public static void addRole(Member member, String roleNameIgnoreCase) {
        Guild g = member.getGuild();
        Role r = g.getRolesByName(roleNameIgnoreCase, true).get(0);
        System.out.println("[INFO]-> Added The role:" + r + " to " + member);
        if (r != null) {
            g.addRoleToMember(member, r).queue();
        } else {
            System.out.println("Cant find Role By that name");
        }
    }

    public static void addRole(Member member, Role role) {
        Guild g = member.getGuild();
        g.addRoleToMember(member, role).queue();
        System.out.println("[INFO]-> Added The role:" + role + " to " + member);
    }

    public static void addRole(Member member, Long ID) {
        Guild g = member.getGuild();
        Role r = g.getRoleById(ID);
        System.out.println("[INFO]-> Added The role:" + r + " to " + member);
        if (r != null) {
            g.addRoleToMember(member, r).queue();
        } else {
            System.out.println("Cant find Role By that name");
        }
    }

    public static void removeRole(Member member, String roleNameIgnoreCase) {
        Guild g = member.getGuild();
        Role r = g.getRolesByName(roleNameIgnoreCase, true).get(0);
        if (r != null) {
            g.removeRoleFromMember(member, r).queue();
            System.out.println("[INFO]-> Removed The role:" + r + " from " + member);
        } else {
            System.out.println("Cant find Role By that name");
        }

    }

    public static void removeRole(Member member, Role role) {
        Guild g = member.getGuild();
        g.removeRoleFromMember(member, role).queue();
        System.out.println("[INFO]-> Removed The role:" + role + " from " + member);
    }

    public static void removeRole(Member member, Long ID) {
        Guild g = member.getGuild();
        Role r = g.getRoleById(ID);
        System.out.println("[INFO]-> Removed The role:" + r + " from " + member);
        if (r != null) {
            g.removeRoleFromMember(member, r).queue();
        } else {
            System.out.println("Cant find Role By that name");
        }
    }
}