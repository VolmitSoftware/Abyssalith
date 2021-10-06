package com.volmit.abyssalith.commands.moderation.warning;

import com.volmit.abyssalith.handlers.WarningHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


public class AddWarn extends VolmitCommand {
    // Constructor
    public AddWarn() {
        super(
                "warn",
                new String[]{"warn", "wn", "addwarn"},
                new String[]{Kit.get().RoleModerator, Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This command Applies Warnings to the person mentioned, or prints them",
                true,
                "mod warn @psycho <warning>"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("MESSAGE: "+ e.getMessage().getContentRaw());

        if(e.getMessage().getMentionedMembers().size() == 1){
            Member m = e.getMessage().getMentionedMembers().get(0);
            WarningHandler.warn(m, e);
        }
        e.getMessage().delete().queue();

    }
}