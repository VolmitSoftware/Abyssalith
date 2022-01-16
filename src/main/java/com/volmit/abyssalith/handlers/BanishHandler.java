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

import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;


public class BanishHandler {

    public static void bok(Member m) {
        if (m.getGuild().getRolesByName(Kit.get().roleBanished, true).size() == 1) {
            Role banishedRole = m.getGuild().getRolesByName(Kit.get().roleBanished, true).get(0);
            RoleHandler.addRole(m, Kit.get().roleBanished);
        }
    }

    public static boolean ifbok(Member m) {
        return (m.getRoles().contains(m.getGuild().getRolesByName(Kit.get().roleBanished, true).get(0)));
    }

    public static void unbok(Member m) {
        Role banishedRole = m.getGuild().getRolesByName(Kit.get().roleBanished, true).get(0);
        RoleHandler.removeRole(m, Kit.get().roleBanished);
    }
}