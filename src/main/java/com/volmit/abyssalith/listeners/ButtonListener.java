/*
 * Abyssalith is a Discord Bot for Volmit Software's Community
 * Copyright (c) 2021 VolmitSoftware (Arcane Arts)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.volmit.abyssalith.listeners;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class ButtonListener extends ListenerAdapter {

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
