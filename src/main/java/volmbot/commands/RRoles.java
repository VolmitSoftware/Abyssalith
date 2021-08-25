package volmbot.commands;

import art.arcane.quill.format.Form;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import volmbot.Main;
import volmbot.data.User;
import volmbot.toolbox.Toolkit;
import volmbot.util.VolmitCommand;
import volmbot.util.VolmitEmbed;

import java.util.List;

public class RRoles extends VolmitCommand {
    // Constructor
    public RRoles() {
        super(
                "ReactionRoles",
                new String[]{"roles","rr"},
                new String[]{Toolkit.get().AdminRole}, // Always permitted if empty. User must have at least one if specified.
                "Tutorial for the Reaction-Role System.",
                false,
                null
        );
    }
    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        VolmitEmbed embed = new VolmitEmbed(" REACTION ROLES!!", e.getMessage());
        User u = Main.getLoader().getUser(e.getAuthor().getIdLong());
        embed.setDescription("*This should explain how to use the reaction roles system, should you have any questions!*");
        embed.addField("Create a Reaction-able Message!"," First of all your message needs to contain the following: `"
                + Toolkit.get().RoleString
                + "`, anywhere in the message, AND you need to be able to view audit log (for now)", false);
        embed.addField("Example: ","" +
                " - " + Toolkit.get().RoleString + " - \n"
                +":coin: for @role1 or :eyes: @role2 even :hearts: for @role3\n"
                +"*__Supports Server Emotes__*", false);
        embed.addField("Adding the reactions!","Finally the author must react to the message with your reactions (*in order of mentioned roles*) to assign them eg: [:coin: :eyes: :hearts:] ", false);
        embed.addField("*Notes*","*This will remove any reactions that are NOT from the Message Author (prevents spam) Unless the rolemaster adds TOO MANY roles, it will clear the reactions for you to react once more*", false);
        embed.send(e.getMessage(), true, 1000);

    }
}