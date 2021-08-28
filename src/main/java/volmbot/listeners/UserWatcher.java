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


            Role r;


            if (xp < 150) {

                if ((e.getGuild().getRolesByName("[n00b]", true).size() != 1)) { // Create the role
                    e.getGuild().createRole().setName("[N00b]").setMentionable(false).setColor(Color.orange).complete();
                }
                r = e.getGuild().getRolesByName("[n00b]", true).get(0); // Manage the role
                e.getGuild().addRoleToMember(e.getMember().getIdLong(), r).queue();

            } else if (xp > 150 && xp < 500) {


            } else if (xp > 1000 && xp < 2000) {

            } else if (xp > 2000 && xp < 4500) {

            } else if (xp > 4500 && xp < 69419) {

            } else if (xp > 69420 && xp < 69425) {

            } else if (xp > 69425 && xp < 9000) {

            }


        }
    }


    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot()) {
            User u = Main.getLoader().getUser(e.getUser().getIdLong());
            u.experience(u.experience() + Toolkit.get().MsgXp.rand());
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
}
