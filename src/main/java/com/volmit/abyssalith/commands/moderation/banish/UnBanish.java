package com.volmit.abyssalith.commands.moderation.banish;

import com.volmit.abyssalith.handlers.BanishHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


public class UnBanish extends VolmitCommand {
    // Constructor
    public UnBanish() {
        super(
                "unbanish",
                new String[]{"unbanish", "unbok", "unsilence"},
                new String[]{Kit.get().RoleModerator, Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This command removes the banished role from the person mentioned, or prints them",
                true,
                "mod unbok @psycho"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Un-Applied Banish: "+ e.getMessage().getContentRaw());

        if(e.getMessage().getMentionedMembers().size() == 1){
            Member m = e.getMessage().getMentionedMembers().get(0);
            BanishHandler.unbok(m);
        }
        e.getMessage().delete().queue();

    }
}