package volmbot.listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import volmbot.Main;
import volmbot.data.User;
import volmbot.toolbox.Toolkit;

public class UserWatcher extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot()) {
            User u = Main.getLoader().getUser(e.getMessage().getAuthor().getIdLong());

            u.experience(u.experience() + Toolkit.get().MsgXp.rand());
            u.messagesSent(u.messagesSent() + 1);
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
