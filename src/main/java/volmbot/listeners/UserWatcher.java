package volmbot.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import volmbot.Main;
import volmbot.data.User;
import volmbot.toolbox.Toolkit;

import java.awt.*;

public class UserWatcher extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot()) {
            User u = Main.getLoader().getUser(e.getMessage().getAuthor().getIdLong()); // USER LOADER
            u.experience(u.experience() + Toolkit.get().MsgXp.rand()); //XP
            u.messagesSent(u.messagesSent() + 1);
            double xp = u.experience();
            double base = Toolkit.get().BaseXpLevel; // 500  (Xp level schema block)
            double bxpm = Toolkit.get().BaseXpMultiplier; // 1.25  (Multiplier)
            int rolerator = 1;

            for (String erole : Toolkit.get().ExperienceRoles) {

                if (xp < base){
                    roleManager(e, Toolkit.get().ExperienceRoles[0]);
                    break;
                }
                if (xp > base && xp < (base*bxpm)*rolerator){
                    roleManager(e, erole);
                    for (String oerole : Toolkit.get().ExperienceRoles) {

                        roleValidator(e, oerole);
                        if (oerole != erole) {
                            Role or = e.getGuild().getRolesByName(oerole, true).get(0);
                            e.getGuild().removeRoleFromMember(e.getMember().getIdLong(), or).queue();
                        }
                    }
                    break;
                }
                rolerator++;
            }
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot()) {
            User u = Main.getLoader().getUser(e.getUser().getIdLong());
            u.experience((u.experience() + Toolkit.get().MsgXp.rand()));
            u.reactions(u.reactions() + 1);
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
        long uid = e.getUserIdLong();
        if (Main.getJDA().getSelfUser().getIdLong() != uid) {
            e.getUserId();
            User u = Main.getLoader().getUser(e.getUserIdLong());
            u.reactions(u.reactions() - 1);
            u.experience(u.experience() - Toolkit.get().MsgXp.rand());
        }
    }

    private void roleManager(GuildMessageReceivedEvent e, String role) {
        Role r;
        if(e.getGuild().getRolesByName(role, false).size() == 1 && e.getGuild().getRolesByName(role, false).contains(e.getGuild().getRolesByName(role, true).get(0)) ) {
            r = e.getGuild().getRolesByName(role, true).get(0);
            if (r != null) {
                e.getGuild().addRoleToMember(e.getMember().getIdLong(), r).queue();
            }
        } else {
            e.getGuild().createRole().setName(role).setMentionable(false).setColor(Color.decode(Toolkit.get().ExperienceRolesColor)).complete();
            r = e.getGuild().getRolesByName(role, true).get(0);
            e.getGuild().addRoleToMember(e.getMember().getIdLong(), r).queue();
        }

    }
    private void roleValidator(GuildMessageReceivedEvent e, String role) {
        if(e.getGuild().getRolesByName(role, false).size() != 1) {
            e.getGuild().createRole().setName(role).setMentionable(false).setColor(Color.decode(Toolkit.get().ExperienceRolesColor)).complete();
        }else if (e.getGuild().getRolesByName(role, false).size() > 1){
            System.out.println("For some reason there are too many roles here im having a stroke...");
        }
    }
}
