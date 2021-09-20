package com.volmit.abyssalith.commands;

import art.arcane.quill.format.Form;
import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.toolbox.Toolkit;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;

import java.util.List;

public class MStats extends VolmitCommand {
    // Constructor
    public MStats() {
        super(
                "ping",
                new String[]{"hello","p"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "This is a debug Ping Keep-alive stats command",
                false,
                null
        );
    }
    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        i("Revealing User Statistics");
        VolmitEmbed embed = new VolmitEmbed(" PONG!", e.getMessage());
        User u = Main.getLoader().getUser(e.getAuthor().getIdLong());
        embed.setDescription("These are your Stats as an example of the return systems enjoy!");
        embed.addField("This is your id!","`"+u.id()+"`", false);
        embed.addField("This is how broke you are!","You have: `"+ Form.f(u.money())+ "` " + Toolkit.get().MoneyName, false);
        embed.addField("This is your Experience!",Form.f(u.experience()) + "**xp**", false);
        embed.addField("This are your Stats","" +
                "Messages sent:"+u.messagesSent()
                +"\n"
                +"Reactions added:"+u.reactions() , false);

        embed.send(e.getMessage(), true, 1000);

    }
}