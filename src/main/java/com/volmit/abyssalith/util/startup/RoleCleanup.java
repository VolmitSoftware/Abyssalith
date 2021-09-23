package com.volmit.abyssalith.util.startup;

import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RoleCleanup extends ListenerAdapter {

    public static void Cleaner(JDA jda) {

        System.println("Cleaning unused roles from guild");
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
            System.println("Cleaned roles: ");
            System.println( ff );
        } else {
            System.println("No roles to clean!");
        }
    }
}
