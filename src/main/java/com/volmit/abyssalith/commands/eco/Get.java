package com.volmit.abyssalith.commands.eco;

import art.arcane.quill.format.Form;
import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.toolbox.Toolkit;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Get extends VolmitCommand {
    // Constructor
    public Get() {
        super(
                "Get", //Name
                new String[]{"bal", "balance"}, //Alias's
                new String[]{Toolkit.get().RoleModerator}, // Always permitted if empty. User must have at least one if specified.
                "Get's the users balance", // Description
                false, // Does it use Args
                "eco get @Psycho" //Example - the prefix
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Eco.Get Instanced");
        String moneyName = Toolkit.get().MoneyName;
        String moneyEmoji = Toolkit.get().MoneyEmoji;
        if (!e.getMessage().getMentionedMembers().isEmpty()) {
            User u = Main.getLoader().getUser(e.getMessage().getMentionedMembers().get(0).getIdLong());

            VolmitEmbed embed = new VolmitEmbed("Balance Page Report!", e.getMessage());
            embed.addField(moneyEmoji + moneyName + " Total: ", Form.f(u.money()), false);
            embed.addField("Total For " + e.getMessage().getMentionedMembers().get(0).getEffectiveName() + ": ", Form.f(u.money()), false);
            embed.send(e.getMessage(), true, 1000);
        } else {
            User u = Main.getLoader().getUser(e.getMessage().getAuthor().getIdLong());
            VolmitEmbed embed = new VolmitEmbed("Balance Page Report!", e.getMessage());
            embed.addField(moneyEmoji + moneyName + " Total: ", Form.f(u.money()), false);
            embed.addField("Total For " + e.getMessage().getAuthor().getName() + ": ", Form.f(u.money()), false);
            embed.send(e.getMessage(), true, 1000);

        }
    }
}
