package com.volmit.abyssalith.listeners.handlers;

import com.volmit.abyssalith.toolbox.Toolkit;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class BanishHandler {

    public static boolean bok(Member m) {
        List<Role> rl = m.getRoles();
        Role banishedRole = m.getGuild().getRolesByName(Toolkit.get().RoleBanished, true).get(0);
        if (rl.isEmpty())
            return false;
        return rl.contains(banishedRole);
    }
}
