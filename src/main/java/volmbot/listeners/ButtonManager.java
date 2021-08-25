package volmbot.listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

public class ButtonManager extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot()) {
            if (e.getMessage().getContentRaw().toLowerCase().contains(".button")) {
                e.getChannel().sendMessage("Click a button").queue(f -> {
                    f.editMessageComponents().setActionRow(
                            Button.primary("primary", "Primary Button"),
                            Button.success("success", "Success Button"),// Button with only an emoji
                            Button.secondary("secondary", "Secondary Button"),// Button with only an emoji
                            Button.danger("danger", "\uD83D\uDC80 ᴰ ᴱ ᴬ ᵀ ᴴ \uD83D\uDC80")// Button with only an emoji
                    ).queue();
                });

            }
        }
    }
}
