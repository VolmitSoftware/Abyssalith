package com.volmit.abyssalith.commands.moderation;

import com.volmit.abyssalith.commands.moderation.banish.Banish;
import com.volmit.abyssalith.commands.moderation.banish.UnBanish;
import com.volmit.abyssalith.commands.moderation.warning.AddWarn;
import com.volmit.abyssalith.commands.moderation.warning.ListWarn;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


public class ModHub extends VolmitCommand {
    // Constructor
    public ModHub() {
        super(
                "mod",
                new String[]{"mod", "m", "moderator"},
                new String[]{Kit.get().RoleModerator,Kit.get().RoleAdministrator}, // Add role name here. Empty: always / 1+: at least one.
                "Moderation Category",
                true,
                "Mod <subcommand>",
                new VolmitCommand[]{
                        new AddWarn(),
                        new ListWarn(),
                        new Banish(),
                        new UnBanish()
                }
        );
    }

    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Warning List Initialized");
        e.getMessage().delete().queue(); // delete the sent message
    }

}