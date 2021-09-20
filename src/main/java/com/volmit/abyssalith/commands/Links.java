package com.volmit.abyssalith.commands;

import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Links extends VolmitCommand {
    // Constructor
    public Links() {
        super(
                "links",
                new String[]{"links","link"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "*Sends useful links (like the wiki)*",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Links Posted");
        VolmitEmbed embed = new VolmitEmbed(" Here you go!", e.getMessage());
        //Commands
        embed.addField("**WIKI LINKS**:", "" +
                "**React Wiki:**\n" +
                "*https://iris-worldgen.gitbook.io/project/getting-started*\n" +
                "**Iris Wiki:**\n" +
                "*https://iris-worldgen.gitbook.io/project/getting-started*\n" +
                "**Wormholes Wiki:**\n" +
                "*https://www.theraleighregister.com/httpswwwyoutubecomwatchvdqw4w9wgxcq5.html*\n", false);
        embed.addField("**OTHER LINKS**:", "" +
                "**Patreon:**\n" +
                "*https://www.patreon.com/volmitsoftware*\n", false);
        embed.send(e.getMessage(), true, 1000);
    }
}