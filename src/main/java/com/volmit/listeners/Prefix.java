package com.volmit.listeners;

import com.volmit.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.volmit.Main;
import com.volmit.toolbox.Toolkit;

public class Prefix extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getMessage().getContentRaw().contains(Main.getJDA().getSelfUser().getId())) {
            String Sender = e.getMessage().getAuthor().getName();
            VolmitEmbed embed = new VolmitEmbed("HEY Don't ping me!", e.getMessage());
            embed
                    .setAuthor("Hello " + Sender)
                    .setDescription("Everytime you @ me, it hurts... Use my prefix please.")
                    .addField("Here is my prefix", "`" + Toolkit.get().BotPrefix + "`", false);

            embed.send(e.getMessage(), true, 1000);
        }
    }
}
