package com.volmit.abyssalith.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import java.util.List;

public class Passive extends VolmitCommand {
    // Constructor
    public Passive() {
        super(
                "passive",
                new String[]{"passives","psv"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "This command shows the passive things im doing while on your server",
                false,
                null
        );
    }
    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Revealing Passive Statistics");
        VolmitEmbed embed = new VolmitEmbed(" Passives!", e.getMessage());
        embed.setDescription("While im on your server im doing a lot of things in the background to make things run really smoothly, and store as minimal data as possible! This command is to show you the transparency that i wished many other bot authors did, but dont.");
        embed.addField("What data am I saving? (USERS)","__Here is a list of what i save for Users__: \n*- User id's and matching XP on a per user basis*\n*- The User's roles in the guild at any time*\n*- Number of Messages & reactions sent or added (i don't save messages at all)*", false);
        embed.addField("What data am I saving? (GUILD)","__Here is a list of what i save for Guilds__: \n*- Guild ID & Chat ID's*\n*- Member ID's (Cached and removed on a per message Basis)*", false);
        embed.addField("Where is it saved?","*Soon to be Redis Server*", false);
        embed.send(e.getMessage(), true, 1000);

    }
}