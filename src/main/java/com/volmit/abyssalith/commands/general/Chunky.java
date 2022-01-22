package com.volmit.abyssalith.commands.general;

import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Chunky extends VolmitCommand {
    public Chunky() {
        super(
                "Chunky or Iris Pregen",
                new String[]{"chunky"},
                new String[0],
                "Why to use Iris' built-in pregenerator and not Chunky",
                false,
                null);
    }

    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        VolmitEmbed em = new VolmitEmbed(
                "Chunky or Iris Pregen",
                e.getMessage()
        );
        em.setDescription("""
                Iris comes with a built-in world generator.
                The generator that we feature is more stable and quick than Chunky in Iris worlds.
                You can of course still use Chunky on vanilla worlds, as it may perform better there.
                
                Iris' pregenerator makes use of the MCA world file system that Minecraft uses,
                and writes directly to those files so the worlds can be generated much more quickly.
        """);
        em.send(e.getMessage(), true);
    }
}
