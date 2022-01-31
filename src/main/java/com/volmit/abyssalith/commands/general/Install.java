package com.volmit.abyssalith.commands.general;

import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Install extends VolmitCommand {
    public Install() {
        super(
                "install",
                new String[]{"install", "1.16", "116", "MC16", "1.17", "1.17", "MC17", "1.18", "MC18", "118"},
                new String[]{},
                "Get installation steps for 1.16.x - 1.18.x",
                false,
                null
        );
    }

    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        e.getMessage().delete().queue();
        e.getChannel().sendMessageEmbeds(new VolmitEmbed()
                .setTitle("Iris Installation Guides")
                .addField(new MessageEmbed.Field("1.18.X (Latest)",
                        "The guide on the wiki is up-to-date for 1.18.X:\n" +
                                "https://docs.volmit.com/iris/getting-started",
                        false
                ))
                .addField(new MessageEmbed.Field("Legacy warning", """
                            Any version older than 1.18 by default has a security exploit known as the Log4j exploit.
                            We **strongly** recommend using 1.18 for this reason, as it makes your and our lives easier.
                            Thank you for your cooperation!
                        """,
                        false
                ))
                .addField(new MessageEmbed.Field("1.17.X", """
                            **1.17.X** is a bit older now and the pack that is automatically downloaded is no longer correct.
                            So you need to take some additional steps. We still recommend using 1.18.X though.
                            If you really cannot use 1.18.X, here is a video: https://youtu.be/WOvaq5ZkXn8
                            
                             - https://api.purpurmc.org/v2/purpur/1.17.1/1428/download
                             - https://www.spigotmc.org/resources/iris-world-gen-custom-biome-colors.84586/download?version=433999
                             - https://github.com/IrisDimensions/overworld/archive/refs/tags/2017.zip
                        """,
                        false
                ))
                .addField(new MessageEmbed.Field("1.16.X", """
                            **1.16.X** is a very old version and that Iris has a lot of bugs in that version.
                            A lot of those bugs have been fixed, so we will not support the version anymore.
                            If you're crazy, here is a video: https://youtu.be/WOvaq5ZkXn8
                            
                             - https://api.purpurmc.org/v2/purpur/1.16.5/1171/download
                             - https://www.spigotmc.org/resources/iris-world-gen-custom-biome-colors.84586/download?version=405004
                             - https://github.com/IrisDimensions/overworld/archive/refs/heads/1.16.zip
                        """,
                        false
                ))
        .build()).queue();
    }
}
