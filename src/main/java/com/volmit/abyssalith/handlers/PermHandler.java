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

public class PermHandler {
    public static boolean hasAdmin(Member m) {
        Role r = m.getGuild().getRolesByName(Kit.get().RoleAdministrator, false).get(0);
        return m.getRoles().contains(r);
    }

    public static boolean hasMod(Member m) {
        Role r = m.getGuild().getRolesByName(Kit.get().RoleModerator, false).get(0);
        return m.getRoles().contains(r);
    }
}
