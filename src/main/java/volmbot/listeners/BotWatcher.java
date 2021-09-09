package volmbot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotWatcher extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().imUser() && !e.getMessage().getEmbeds().isEmpty()) {
            e.getMessage().addReaction("U+274C").queue();
        }
    }

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (e.getUser().isBot()) {
            System.println("Bot reaction captured");
        }else{
            User ou = e.getUser();
            e.getChannel().retrieveMessageById(e.getMessageIdLong()).complete().delete().queue(); // Try and delete the message
            System.println("Is user Bot?" + ou.isBot());
        }
    }
}


