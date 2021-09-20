package com.volmit.abyssalith.commands;

import com.volmit.abyssalith.commands.eco.Get;
import com.volmit.abyssalith.commands.eco.Give;
import com.volmit.abyssalith.commands.eco.Remove;
import com.volmit.abyssalith.commands.eco.Set;
import com.volmit.abyssalith.toolbox.Toolkit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Eco extends VolmitCommand {
    // Constructor
    public Eco() {
        super(
                "eco",
                new String[]{"eco", "money"},
                new String[]{Toolkit.get().RoleModerator}, // Add role name here. Empty: always / 1+: at least one.
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

    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Eco List Initialized");
        e.getMessage().delete().queue(); // delete the sent message
    }

}