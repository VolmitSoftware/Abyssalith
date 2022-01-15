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
package com.volmit.abyssalith.bot.startup;

import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;


public class RoleCleanup extends ListenerAdapter {

    public static void Cleaner(JDA jda) {

        System.out.println("Cleaning unused roles from guild");
        List<String> ff = new ArrayList<>();
        for (Role r : jda.getGuilds().get(0).getRoles()) {
            Role role = jda.getGuilds().get(0).getRoleById(jda.getGuilds().get(0).getRolesByName(r.getName(), false).get(0).getIdLong());
            List<Member> members = jda.getGuilds().get(0).getMembersWithRoles(role);
            if (members.size() == 0 && r.getName().contains(Kit.get().LevelName)) {
                jda.getGuilds().get(0).getRolesByName(r.getName(), true).get(0).delete().queue();
                ff.add(jda.getGuilds().get(0).getRolesByName(r.getName(), true).get(0).getName());
            }
        }
        if (ff.size() > 0) {
            System.out.println("Cleaned roles: ");
            System.out.println(ff);
        } else {
            System.out.println("No roles to clean!");
        }
    }
}
