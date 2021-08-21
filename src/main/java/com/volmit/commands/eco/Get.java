package com.volmit.commands.eco;

import art.arcane.quill.format.Form;
import com.volmit.data.User;
import com.volmit.util.VolmitCommand;
import com.volmit.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import com.volmit.Main;
import com.volmit.toolbox.Toolkit;

import java.util.List;

public class Get extends VolmitCommand {
    // Constructor
    public Get() {
        super(
                "Get", //Name
                new String[]{"bal", "balance"}, //Alias's
                new String[]{"ADMINISTRATOR"}, // Always permitted if empty. User must have at least one if specified.
                "Get's the users balance", // Description
                false, // Does it use Args
                "eco get @Psycho" //Example - the prefix
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        String moneyName = Toolkit.get().MoneyName;
        String moneyEmoji = Toolkit.get().MoneyEmoji;

        User u = Main.getLoader().getUser(e.getMessage().getMentionedMembers().get(0).getIdLong());

        VolmitEmbed embed = new VolmitEmbed("Transaction Receipt!", e.getMessage());
        embed.addField(moneyEmoji+ moneyName+ " Total: ", Form.f(u.money()) + " Requested  By: " + e.getAuthor().getAsMention(), false);
        embed.addField("Total For " + e.getMessage().getMentionedMembers().get(0).getEffectiveName() + ": ", Form.f(u.money()), false);
        embed.send(e.getMessage(), true, 1000);
    }
}
