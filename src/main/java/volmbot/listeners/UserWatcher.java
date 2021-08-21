package volmbot.listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import volmbot.Main;
import volmbot.data.User;

import java.util.concurrent.ThreadLocalRandom;

public class UserWatcher extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot()){
            User u = Main.getLoader().getUser(e.getMessage().getAuthor().getIdLong());
            long v = ThreadLocalRandom.current().nextLong(0,3);

            u.experience(u.experience() + v);
            u.messagesSent(u.messagesSent()+1);
        }
    }
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot()){
            User u = Main.getLoader().getUser(e.getUser().getIdLong());
            u.reactions(u.reactions() + 1);
        }
    }
}
