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
        e.getChannel().sendMessageEmbeds(new VolmitEmbed()
                .setTitle("Iris Installation Guides")
                .addField(new MessageEmbed.Field("Latest", """
                            The guide on the wiki is up-to-date for our latest release, see:
                            https://docs.volmit.com/iris/getting-started
                            Never update your Iris version without resetting all Iris worlds.
                        """,
                        false
                ))
                .addField(new MessageEmbed.Field("Legacy warning", """
                            Any version older than the most recent release is likely to contain more bugs than the latest release.
                            Any version older than 1.18 by default has a security exploit known as the Log4j exploit.
                            We **strongly** recommend using a more recent version (like latest, hint hint) for this reason.
                            On top of making your life easier, it makes our lives easier too.
                            Never update your Iris version without resetting all Iris worlds.
                            Thank you for your cooperation!
                        """,
                        false
                ))
                .addField(new MessageEmbed.Field("Older", """
                            1.17 and before are deprecated. We no longer support these versions, and neither should you.
                            There may or may not be a video on youtube explaining how to use these versions on Iris, but I'm not giving it out of principle.
                            We recommend using the latest version because it is simply the most stable.
                        """,
                        false
                ))
        .build()).queue();
    }
}
