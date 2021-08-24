package volmbot.listeners;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import volmbot.toolbox.Toolkit;

import java.util.concurrent.ThreadLocalRandom;

public class OwOListener extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot()) {
            if (e.getMessage().getContentRaw().toLowerCase().contains("owo")) {
                int rand = ThreadLocalRandom.current().nextInt(5) % Toolkit.get().owo.size();
                String randomElement = Toolkit.get().owo.get(rand);
                int r = ThreadLocalRandom.current().nextInt(10);
                if (r <= 1) {
                    e.getChannel().sendMessage(randomElement).setActionRow(Button.primary("hello", "Click Me"));


                }
            }
        }
    }


    @Override
    public void onButtonClick(ButtonClickEvent event) {
        if (event.getComponentId().equals("hello")) {
            event.reply("Hello :)").queue(); // send a message in the channel
        } else if (event.getComponentId().equals("emoji")) {
            event.editMessage("That button didn't say click me").queue(); // update the message
        }
    }
}