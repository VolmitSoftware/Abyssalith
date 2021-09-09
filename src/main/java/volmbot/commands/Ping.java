package volmbot.commands;

import art.arcane.quill.format.Form;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import volmbot.Main;
import volmbot.data.User;
import volmbot.toolbox.Toolkit;
import volmbot.util.VolmitCommand;
import volmbot.util.VolmitEmbed;
import java.util.List;

public class Ping extends VolmitCommand {
    // Constructor
    public Ping() {
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
        VolmitEmbed embed = new VolmitEmbed(" Passives!", e.getMessage());
        User u = Main.getLoader().getUser(e.getAuthor().getIdLong());
        embed.setDescription("While im on your server im doing a lot of things in the background to make things run really smoothly, and store as minimal data as possible! This command is to show you the transparency that i wished many other bot authors did, but dont.");
        embed.addField("What data am I saving? (USERS)","Here is a list of what i save: User id's and matching XP on a per user basis\nNumber if Messages / reactions sent or added (i don't save messages at all)", false);
        embed.addField("What data am I saving? (USERS)","Here is a list of what i save: Guild ID & Chat ID's\nMember ID's (Cached and removed on a per message Basis)", false);
        embed.send(e.getMessage(), true, 1000);

    }
}