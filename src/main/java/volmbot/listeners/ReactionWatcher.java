package volmbot.listeners;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import volmbot.Main;
import volmbot.data.User;

import java.util.concurrent.ThreadLocalRandom;

public class ReactionWatcher extends ListenerAdapter {
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot()) {
            e.getUserId();
            User u = Main.getLoader().getUser(e.getUserIdLong());
            u.reactions(u.reactions());
            long v = ThreadLocalRandom.current().nextLong(0,1);
            u.experience(u.experience() + v);
        }
    }


    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e) {
        long uid = e.getUserIdLong();
        if (Main.getJDA().getSelfUser().getIdLong() != uid) {
            e.getUserId();
            User u = Main.getLoader().getUser(e.getUserIdLong());
            u.reactions(u.reactions()-1);
            long v = ThreadLocalRandom.current().nextLong(0,1);
            u.experience(u.experience() - v);
        }
    }
}