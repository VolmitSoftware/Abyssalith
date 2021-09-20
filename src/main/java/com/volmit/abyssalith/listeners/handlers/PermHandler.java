package com.volmit.abyssalith.listeners.handlers;

import com.volmit.abyssalith.toolbox.Toolkit;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class PermHandler {
    public static boolean hasAdmin(Member m){
        Role r = m.getGuild().getRolesByName(Toolkit.get().RoleAdministrator, false).get(0);
        return m.getRoles().contains(r);
    }
    public static boolean hasMod(Member m){
        Role r = m.getGuild().getRolesByName(Toolkit.get().RoleModerator, false).get(0);
        return m.getRoles().contains(r);
    }
}
