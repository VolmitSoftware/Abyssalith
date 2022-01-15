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
package com.volmit.abyssalith.commands.moderation.eco;

import art.arcane.quill.format.Form;
import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;


public class Get extends VolmitCommand {
    // Constructor
    public Get() {
        super(
                "Get", //Name
                new String[]{"bal", "balance"}, //Alias's
                new String[]{Kit.get().RoleModerator}, // Always permitted if empty. User must have at least one if specified.
                "Get's the users balance", // Description
                false, // Does it use Args
                "eco get @Psycho" //Example - the prefix
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Abyss.info("Eco.Get Instanced");
        String moneyName = Kit.get().MoneyName;
        String moneyEmoji = Kit.get().MoneyEmoji;
        if (!e.getMessage().getMentionedMembers().isEmpty()) {
            User u = Abyss.getLoader().getUser(e.getMessage().getMentionedMembers().get(0).getIdLong());

            VolmitEmbed embed = new VolmitEmbed("Balance Page Report!", e.getMessage());
            embed.addField(moneyEmoji + moneyName + " Total: ", Form.f(u.money()), false);
            embed.addField("Total For " + e.getMessage().getMentionedMembers().get(0).getEffectiveName() + ": ", Form.f(u.money()), false);
            embed.send(e.getMessage(), true, 1000);
        } else {
            User u = Abyss.getLoader().getUser(e.getMessage().getAuthor().getIdLong());
            VolmitEmbed embed = new VolmitEmbed("Balance Page Report!", e.getMessage());
            embed.addField(moneyEmoji + moneyName + " Total: ", Form.f(u.money()), false);
            embed.addField("Total For " + e.getMessage().getAuthor().getName() + ": ", Form.f(u.money()), false);
            embed.send(e.getMessage(), true, 1000);

        }
    }
}
