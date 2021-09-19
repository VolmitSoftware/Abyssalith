package volmbot.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import volmbot.Main;
import volmbot.data.User;

import java.util.Objects;
import java.util.Set;

public class PersistentRoleListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if (!e.getMember().getUser().isBot()) {
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
        if (!e.getMember().getUser().isBot()) {
            User u = Main.getLoader().getUser(Objects.requireNonNull(e.getMember()).getIdLong()); // Load the user object
            u.roleIds().add(e.getRoles().get(0).getId());
            i("Attached role to : " + e.getMember().getEffectiveName() + "Role ID: " + e.getRoles().get(0).getId());
        }
    }

    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent e) {
        if (!e.getMember().getUser().isBot()) {
            User u = Main.getLoader().getUser(Objects.requireNonNull(e.getMember()).getIdLong()); // Load the user object
            u.roleIds().remove(e.getRoles().get(0).getId());
            i("Removed role from : " + e.getMember().getEffectiveName() + " Role ID: " + e.getRoles().get(0).getId());
        }
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) { // THIS IS A FALLBACK TO KEEP ROLES UPDATED
        User u = Main.getLoader().getUser(Objects.requireNonNull(e.getMember()).getIdLong()); // Load the user object
        Set<String> lRoles = u.roleIds(); // Load the Roles from the user file
        if (lRoles.size() < e.getMember().getRoles().size() && !e.getMember().getUser().isBot()) {
            for (Role r : e.getMember().getRoles()) {
                u.roleIds().add(r.getId());
            }
            i("Found Missing Roles, Rebinding to : " + e.getMember().getEffectiveName());
        }
    }
}
