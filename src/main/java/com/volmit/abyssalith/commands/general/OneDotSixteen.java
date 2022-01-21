package com.volmit.abyssalith.commands.general;

import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class OneDotSixteen extends VolmitCommand {
    public OneDotSixteen() {
        super(
                "1.16",
                new String[]{"1.16", "116", "MC16"},
                new String[]{},
                "Get the 1.16 installation video for those stupid enough to use it",
                false,
                null
        );
    }

    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        e.getMessage().replyEmbeds(new VolmitEmbed()
                .setTitle("1.16 tutorial video")
                .addField(new MessageEmbed.Field("1.16 Disclaimer",
                        "Please note that 1.16 is a very old version and " +
                                "that Iris has a lot of bugs in that version. " +
                                "Minecraft itself also has known security exploits in that version. " +
                                "We strongly recommend choosing a more recent version like 1.18",
                        true))
        .build()).queue();
        e.getMessage().reply("https://youtu.be/WOvaq5ZkXn8").queue();
    }
}
