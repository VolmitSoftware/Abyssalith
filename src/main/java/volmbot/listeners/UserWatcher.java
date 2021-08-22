package volmbot.listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import volmbot.Main;
import volmbot.data.User;

import java.util.concurrent.ThreadLocalRandom;

public class UserWatcher extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot()) {
            User u = Main.getLoader().getUser(e.getMessage().getAuthor().getIdLong());
            double v = ThreadLocalRandom.current().nextDouble(0.8, 1.0);

            u.experience(u.experience() + v);
            u.messagesSent(u.messagesSent() + 1);
        }
    }


    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot()) {
            User u = Main.getLoader().getUser(e.getUser().getIdLong());
            double v = ThreadLocalRandom.current().nextDouble(0.01, 0.025);
            u.experience(u.experience() + v);
            u.reactions(u.reactions() + 1);
        }
    }

    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
        long uid = e.getUserIdLong();
        if (Main.getJDA().getSelfUser().getIdLong() != uid) {
            e.getUserId();
            User u = Main.getLoader().getUser(e.getUserIdLong());
            u.reactions(u.reactions() - 1);
            double v = ThreadLocalRandom.current().nextDouble(0.01, 0.025);
            u.experience(u.experience() - v);
        }
    }
}
