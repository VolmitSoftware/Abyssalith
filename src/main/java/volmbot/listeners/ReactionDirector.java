package volmbot.listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class ReactionDirector extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot() && e.getMember().hasPermission(Permission.VIEW_AUDIT_LOGS) && e.getMessage().getMentionedRoles().size() > 0) {
            //TODO IMPLEMENT ROLE SYSTEM LATER, .haspermission cant return null ever, retarded api ^
            String Message = e.getMessage().getContentRaw().toLowerCase();
            if (Message.contains("fff")) { // Check the descriminator
                List<Role> oRole = e.getMessage().getMentionedRoles();
                for (Role role : oRole) { // iterate the roles
                    System.out.println(role);

                }

            }

        }

    }


    //TODO, actually get it to work
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        Message m = e.getChannel().retrieveMessageById(e.getMessageIdLong()).complete();

        System.out.println(m.getAuthor().getIdLong()); // Get the message author
        System.out.println(e.getMember().getUser().getIdLong());


        if (/*!m.getAuthor().getId().equals(e.getMember().getId()) &&*/ m.getMentionedRoles().size() > 0) {

            String Message = m.getContentRaw().toLowerCase();
            if (Message.contains("fff")) {
                List<MessageReaction> oReaction = m.getReactions();
                List<Role> oRole = m.getMentionedRoles();

                System.out.println("[oRole]: "+oRole);
                System.out.println("[oReaction]: "+ oReaction);

                int iter = 0;

                for (MessageReaction reaction : oReaction) {

                    if(true){

                        System.out.println("[TRUE] Is a React Role: " +reaction);


                        Role mr = oRole.get(iter);
                        //e.getGuild().addRoleToMember(e.getUserIdLong(), mr).queue();
                    }else {
                        System.out.println("[FAILED] Is NOT React Role: " +reaction);
                    }


                    iter++;
                }
                for (Role role : oRole) {
                    System.out.println(role);
                }

            }

        }

    }

}

