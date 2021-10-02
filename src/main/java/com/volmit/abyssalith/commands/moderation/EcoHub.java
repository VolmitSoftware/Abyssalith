package com.volmit.abyssalith.commands.moderation;

import com.volmit.abyssalith.commands.moderation.eco.Get;
import com.volmit.abyssalith.commands.moderation.eco.Give;
import com.volmit.abyssalith.commands.moderation.eco.Remove;
import com.volmit.abyssalith.commands.moderation.eco.Set;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


public class EcoHub extends VolmitCommand {
    // Constructor
    public EcoHub() {
        super(
                "eco",
                new String[]{"economy", "eco", "money"},
                new String[]{Kit.get().RoleModerator,Kit.get().RoleAdministrator}, // Add role name here. Empty: always / 1+: at least one.
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