package com.volmit.abyssalith.commands.moderation.warning;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.handlers.WarningHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


public class ListWarn extends VolmitCommand {
    // Constructor
    public ListWarn() {
        super(
                "listwarns",
                new String[]{"listwarns", "warns", "lw"},
                new String[]{Kit.get().RoleModerator, Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This command lists all of the warnings for a user",
                false,
                "mod warns <ID>"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        String[] s = e.getMessage().getContentRaw().split(" ");
        if (e.getMessage().getMentionedMembers().size() == 0 && e.getGuild().getMemberById(s[2].toString()) != null) {
            User u = Main.getLoader().getUser(e.getGuild().getMemberById(s[2].toString()).getIdLong());

            WarningHandler.warnShow(u, e.getChannel());

        }
        e.getMessage().delete().queue();


    }
}