package com.volmit.commands.eco;

import art.arcane.quill.format.Form;
import com.volmit.data.User;
import com.volmit.util.VolmitCommand;
import com.volmit.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import com.volmit.Main;
import com.volmit.toolbox.Toolkit;

import java.util.List;

public class Remove extends VolmitCommand {
    // Constructor
    public Remove() {
        super(
                "remove", //Name
                new String[]{}, //Alias's
                new String[]{"ADMINISTRATOR"}, // Always permitted if empty. User must have at least one if specified.
                "Takes a Number of currency to the user", // Description
                true, // Does it use Args
                "eco remove 10 @Psycho" //Example - the prefix
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        String moneyName = Toolkit.get().MoneyName;
        String moneyEmoji = Toolkit.get().MoneyEmoji;

        VolmitEmbed embed = new VolmitEmbed("Transaction Receipt!", e.getMessage());
        embed.addField(moneyEmoji+ moneyName+ " removed: ", args.get(1) + " Removed By: " + e.getAuthor().getAsMention(), false);

        User u = Main.getLoader().getUser(e.getMessage().getMentionedMembers().get(0).getIdLong());

        u.money(Integer.parseInt(args.get(1)) - u.money());

        embed.addField("New Total For " + e.getMessage().getMentionedMembers().get(0).getEffectiveName() + ": ", Form.f(u.money()), false);
        embed.send(e.getMessage(), true, 1000);
    }
}
