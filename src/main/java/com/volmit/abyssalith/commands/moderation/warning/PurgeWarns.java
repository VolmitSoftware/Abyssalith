package com.volmit.abyssalith.commands.moderation.warning;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.handlers.WarningHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


public class PurgeWarns extends VolmitCommand {
    // Constructor
    public PurgeWarns() {
        super(
                "purgewarns",
                new String[]{"purgewarns", "purge", "pw"},
                new String[]{Kit.get().RoleModerator, Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This command removes all warnings from a user",
                false,
                "mod purgewarns <ID>"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        String[] s = e.getMessage().getContentRaw().split(" ");
        if (e.getMessage().getMentionedMembers().size() == 0 && e.getGuild().getMemberById(s[2].toString()) != null) {
            User u = Main.getLoader().getUser(e.getGuild().getMemberById(s[2].toString()).getIdLong());

            WarningHandler.purge(u, e.getMessage().getTextChannel());

        }
        e.getMessage().delete().queue();


    }
}