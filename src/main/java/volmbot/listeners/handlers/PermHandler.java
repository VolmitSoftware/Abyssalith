package volmbot.listeners.handlers;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import volmbot.toolbox.Toolkit;

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
