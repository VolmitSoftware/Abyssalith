package volmbot.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import volmbot.commands.eco.*;
import volmbot.util.VolmitCommand;

import java.util.List;

public class Eco extends VolmitCommand {
    // Constructor
    public Eco() {
        super(
                "eco",
                new String[]{},
                new String[]{}, // Add role name here. Empty: always / 1+: at least one.
                "Economy Category",
                true,
                "Eco <subcommand>",
                new VolmitCommand[]{
                        new Give(),
                        new Set(),
                        new Remove(),
                        new Get(),
                }
        );
    }

}