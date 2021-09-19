package volmbot.listeners;

import art.arcane.quill.execution.J;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().imUser() && !e.getMessage().getEmbeds().isEmpty()) {
            e.getMessage().addReaction("U+274C").queue();
        }
    }


    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        if (!e.getUser().isBot() && e.getChannel().retrieveMessageById(e.getMessageId()).complete().getAuthor().isBot() && e.getReaction().toString().contains("U+274c")) {

            J.a(() -> {
                J.sleep(1000);
                i("[INFO]: Cleaning bot response as requested");
                e.getChannel().retrieveMessageById(e.getMessageIdLong()).complete().delete().queue(); // Try and delete the message
            });
        }
    }
}


