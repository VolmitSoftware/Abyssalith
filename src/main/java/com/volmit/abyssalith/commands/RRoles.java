package com.volmit.abyssalith.commands;

import com.volmit.abyssalith.commands.rroles.MentionRoles;
import com.volmit.abyssalith.toolbox.Toolkit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class RRoles extends VolmitCommand {
    // Constructor
    public RRoles() {
        super(
                "ReactionRoles",
                new String[]{"reactionRoles", "roles", "rr"},
                new String[]{Toolkit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "Entering this command gives you a tutorial on how to use the Reaction-Role System.",
                true,
                "rr <subcommand>",
                new VolmitCommand[]{
                        new MentionRoles()
                }
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Reaction Role List Initialized");
        e.getMessage().delete().queue(); // delete the sent message
    }
}