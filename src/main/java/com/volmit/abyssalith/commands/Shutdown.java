package com.volmit.abyssalith.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.toolbox.Toolkit;
import com.volmit.abyssalith.util.VolmitCommand;

import java.util.List;

public class Shutdown extends VolmitCommand {
    public static boolean checkOverrideAdmin = false;

    // Constructor
    public Shutdown() {
        super(
                "stop",
                new String[]{"stop","kill","s"},
                new String[]{Toolkit.get().RoleAdministrator}, // Add role name here. Empty: always / 1+: at least one.
                "Stops the Bot boi",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        w("Terminating the Bot");
        String oidcheck = e.getMessage().getAuthor().getId();
        if (oidcheck.equals(Toolkit.get().BotOwnerID)) {
            Main.warn("KILLING BOT");
            e.getMessage().delete().queue();
            Main.shutdown();
        } else {
            e.getChannel().sendMessage("uR noT my DAddY!").queue();
            checkOverrideAdmin = true;
        }
    }
}
