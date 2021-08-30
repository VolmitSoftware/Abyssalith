package volmbot.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import volmbot.toolbox.Toolkit;

public class PermHandler {

    public static boolean hasAdmin(Member m){
        Role r = m.getGuild().getRolesByName(Toolkit.get().AdminRole, false).get(0);
        return m.getRoles().contains(r);
    }
    public static boolean hasMod(Member m){
        Role r = m.getGuild().getRolesByName(Toolkit.get().ModRole, false).get(0);
        return m.getRoles().contains(r);
    }
}
