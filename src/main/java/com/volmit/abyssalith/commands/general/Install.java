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
        VolmitEmbed em = new VolmitEmbed("Iris Installation Guides", e.getMessage());
        em.addField(new MessageEmbed.Field(
                "1.18.X (Latest)",
                """
                The guide on the wiki is up-to-date for 1.18.X:
                https://docs.volmit.com/iris/getting-started""",
                true
        ));
        em.addField(new MessageEmbed.Field("1.16.X (Unsupported) & 1.17.X (Supported)", """
                Please note that 1.16 is a very old version and that Iris has a lot of bugs in that version.
                1.17 is now no longer the most recent version, and we will drop support for it too.
                On top of being old and Iris having bugs,
                Minecraft has a known security exploit in both versions
                that puts your server at risk of being hacked and destroyed.
                With that said, here is a video tutorial for those
                reckless enough to make a new Iris world on these versions:
                Legacy install video here: https://youtu.be/WOvaq5ZkXn8
            """,
            true
        ));
        em.send(e.getMessage(), true);
    }
}
