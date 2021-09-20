package com.volmit.abyssalith.listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

public class PasteListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().contains("https://pastebin.com/")) {
            i("Started the Paste Service");
            String str = e.getMessage().getContentRaw();
            String[] pbArr = str.split(" ");
            for (String s : pbArr) {
                if (s.contains("https://pastebin.com/")) {
                    e.getChannel().sendMessage("Would you like me to scan this for you?: <" + s + ">").queue(f -> {
                        f.editMessageComponents().setActionRow(
                                Button.success("pastbinlinknew", "Yes please!"),
                                Button.danger("no", "No, go away!")
                        ).queue();
                        i("Sent Paste Service Buttons");
                    });
                }
            }
        }
    }

}
