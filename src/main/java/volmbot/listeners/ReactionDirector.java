package volmbot.listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class ReactionDirector extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot() && e.getMember().hasPermission(Permission.VIEW_AUDIT_LOGS)) { //TODO IMPLEMENT ROLE SYSTEM LATER, .haspermission cant return null ever, retarded api

            if (e.getMessage().getMentionedRoles().size() > 0) {

                String Message = e.getMessage().getContentRaw().toLowerCase();
                if (Message.contains("fff")) {

                    List<Role> oRole = e.getMessage().getMentionedRoles();
                    for (Role role : oRole) {
                        System.out.println(role);

                    }

                }

            }

        }

    }

    //todo , actually get it to work
//    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
//        Message m = e.getChannel().getHistory().getMessageById(e.getMessageIdLong());
//        System.out.println(e.getMessageId());
//        if (  ){
//            System.out.println("dwa" + m.getAuthor().getId());
//            System.out.println("ddd" + e.getMember().getId());
//
//
//        }
//
//    }

}

