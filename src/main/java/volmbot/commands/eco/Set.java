package volmbot.commands.eco;

import art.arcane.quill.format.Form;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import volmbot.Main;
import volmbot.data.User;
import volmbot.toolbox.Toolkit;
import volmbot.util.VolmitCommand;
import volmbot.util.VolmitEmbed;

import java.util.List;

public class Set extends VolmitCommand {
    // Constructor
    public Set() {
        super(
                "set", //Name
                new String[]{}, //Alias's
                new String[]{Toolkit.get().ModRole}, // Always permitted if empty. User must have at least one if specified.
                "Sets the users balance", // Description
                true, // Does it use Args
                "eco set 10 @Psycho" //Example - the prefix
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        String moneyName = Toolkit.get().MoneyName;
        String moneyEmoji = Toolkit.get().MoneyEmoji;

        VolmitEmbed embed = new VolmitEmbed("Transaction Receipt!", e.getMessage());
        embed.addField(moneyEmoji+ moneyName+ " set: ", args.get(1) + " Set By: " + e.getAuthor().getAsMention(), false);

        User u = Main.getLoader().getUser(e.getMessage().getMentionedMembers().get(0).getIdLong());
        u.money(Float.parseFloat(args.get(1)));


        embed.addField("New Total For " + e.getMessage().getMentionedMembers().get(0).getEffectiveName() + ": ", Form.f(u.money()), false);
        embed.send(e.getMessage(), true, 1000);
    }
}
