package com.volmit.abyssalith.listeners.handlers;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonHandler extends ListenerAdapter {

    @Override
    public void onButtonClick(ButtonClickEvent e) { //TODO--------------THIS  IS THE BUTTON MANAGER---------------------//
        if (e.getComponentId().equals("success")) {
            e.getUser().openPrivateChannel().complete().sendMessage("Hello!").queue();
            e.editButton(e.getButton()).complete();

        } else if (e.getComponentId().equals("secondary")) {
            e.getUser().openPrivateChannel().complete().sendMessage("Hello!").queue();
            e.editButton(e.getButton()).complete();

        }
    }
}


//--------HOW TO MAKE A BUTTON-----------//
//public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
//    if (!e.getMessage().getAuthor().isBot()) {
//        if (e.getMessage().getContentRaw().toLowerCase().contains(".button")) {
//            e.getChannel().sendMessage("Click a button").queue(f -> {
//                f.editMessageComponents().setActionRow(
//                        Button.primary("primary", "Primary Button"),
//                        Button.success("success", "Success Button"),// Button with only an emoji
//                        Button.secondary("secondary", "Secondary Button"),// Button with only an emoji
//                        Button.danger("danger", "\uD83D\uDC80 ᴰ ᴱ ᴬ ᵀ ᴴ \uD83D\uDC80")// Button with only an emoji
//                ).queue();
//            });
//
//        }
//    }
//}