package volmbot.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import volmbot.Main;
import volmbot.data.User;
import volmbot.toolbox.Toolkit;
import volmbot.util.XP;

import java.awt.Color;

public class UserListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getMessage().imUser()) {
            User u = Main.getLoader().getUser(e.getMessage().getAuthor().getIdLong()); // USER LOADER
            u.experience(u.experience() + Toolkit.get().XpPerMessage.rand()); //XP
            u.messagesSent(u.messagesSent() + 1);
            double uxp = u.experience();
            int validator = XP.getLevelForXp(uxp);

            if (validator < Toolkit.get().XpMaxLevels) {
                roleValidator(e, Toolkit.get().LevelName + XP.getLevelForXp(uxp));
                roleManager(e, Toolkit.get().LevelName + XP.getLevelForXp(uxp), validator);
            }
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot()) {
            User u = Main.getLoader().getUser(e.getUser().getIdLong());
            u.experience((u.experience() + Toolkit.get().XpPerMessage.rand()));
            u.reactions(u.reactions() + 1);
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
        long uid = e.getUserIdLong();
        if (Main.getJDA().getSelfUser().getIdLong() != uid) {
            e.getUserId();
            User u = Main.getLoader().getUser(e.getUserIdLong());
            u.reactions(u.reactions() - 1);
            u.experience(u.experience() - Toolkit.get().XpPerMessage.rand());
        }
    }

    private void roleManager(GuildMessageReceivedEvent e, String role, int v) {
        Role r;
        Role rv;
        int vint = v - 1;

        if (e.getGuild().hasRole(role)) {
            r = e.getGuild().getRolesByName(role, true).get(0);
            if (r != null) {
                e.getGuild().addRoleToMember(e.getMember().getIdLong(), r).queue();
                if (v > 0) {
                    try {
                        rv = e.getGuild().getRolesByName(Toolkit.get().LevelName + vint, true).get(0);
                        e.getGuild().removeRoleFromMember(e.getMember().getIdLong(), rv).queue();
                    } catch (Exception ignored) {
                    }
                }
            }
        } else {
            e.getGuild().createRole().setName(role).setMentionable(false).setColor(Color.decode(Toolkit.get().XpRoleColor)).complete();
            i("New Maximum level created!");
            r = e.getGuild().getRolesByName(role, true).get(0);
            rv = e.getGuild().getRolesByName(Toolkit.get().LevelName + vint, true).get(0);
            e.getGuild().removeRoleFromMember(e.getMember().getIdLong(), rv).queue();
            e.getGuild().addRoleToMember(e.getMember().getIdLong(), r).queue();
        }

    }

    private void roleValidator(GuildMessageReceivedEvent e, String role) {
        if (e.getGuild().getRolesByName(role, false).size() < 1) {
            e.getGuild().createRole().setName(role).setMentionable(false).setColor(Color.decode(Toolkit.get().XpRoleColor)).complete();
            i("[RV] - New Maximum level created!");
        } else if (e.getGuild().getRolesByName(role, false).size() > 1) {
            w("For some reason there are too many roles here im having a stroke...");
        }
    }
}
