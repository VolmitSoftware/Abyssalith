package volmbot.listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import volmbot.toolbox.Toolkit;

import java.util.List;

public class ReactionDirector extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        System.out.println("The size is: " + e.getMessage().getMentionedRoles().size());
        if (!e.getMessage().getAuthor().isBot() && e.getMember().hasPermission(Permission.VIEW_AUDIT_LOGS) && e.getMessage().getMentionedRoles().size() > 0) {
            //TODO IMPLEMENT ROLE SYSTEM LATER, .haspermission can't return null ever, retarded api ^
            String Message = e.getMessage().getContentRaw().toLowerCase();
            if (Message.toLowerCase().contains(Toolkit.get().RoleString.toLowerCase())) { // Check the descriminator
                if (e.getMessage().getMentionedRoles().size() > 2) {

                    SelectionMenu.Builder menu = SelectionMenu.create("menu:rolepage").setPlaceholder("Choose your Role(s)!");// shows the placeholder indicating what this menu is for
                    menu.addOption("REMOVE ALL ROLES", "role-remove", Emoji.fromUnicode("\uD83D\uDEAB")); //TODO implement this system later

                    List<Role> oRole = e.getMessage().getMentionedRoles();
                    for (Role role : oRole) { // iterate the roles
                        List<Emote> em = e.getGuild().getEmotesByName(role.getName(), true);
                        Emoji use = null;

                        if(em.size() >= 1){
                            use = Emoji.fromEmote(em.get(0));
                        }else{
                            use = Emoji.fromUnicode("\uD83E\uDE84");
                        }
                        menu.addOption(role.getName(), role.getId(), "This gives you the role: "+ role.getName(), use);
                    }

                    e.getChannel().sendMessage("React to the roles that you want!")
                            .setActionRow(menu.build())
                            .queue(f -> {
                            });
                    //e.getMessage().delete().queue();
                }
            }
        }
    }


    //TODO, actually get it to work
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
        Message m = e.getChannel().retrieveMessageById(e.getMessageIdLong()).complete();
        String Message = m.getContentRaw().toLowerCase();

        if (Message.toLowerCase().contains(Toolkit.get().RoleString.toLowerCase())
                && m.getAuthor().getId().equalsIgnoreCase("173261518572486656") //TODO GET AUTHOR ID ELSEWISE
                && m.getMentionedRoles().size() > 0) {

            List<MessageReaction> oReaction = m.getReactions();
            List<Role> oRole = m.getMentionedRoles();

            User oeUser = e.getMember().getUser();
            User authUser = m.getAuthor();

            long oeUserID = oeUser.getIdLong();
            long authUserID = authUser.getIdLong();


            boolean hadRole = false;

            int iter = 0;

            for (MessageReaction reaction : oReaction) {
                if (reaction.retrieveUsers().complete().contains(m.getAuthor()) && reaction.retrieveUsers().complete().contains(oeUser) && oeUserID != authUserID) {
                    Role mr = oRole.get(iter);

                    for (Role role : e.getMember().getRoles()) {

                        if (oRole.get(iter).getName().equals(role.getName())) {
                            System.out.println("[ALREADY HAVE ROLE, REMOVING]: " + iter);
                            e.getGuild().removeRoleFromMember(e.getUserIdLong(), mr).queue(); // Remove role
                            hadRole = true;
                        }

                    }
                    if (!hadRole) {
                        e.getGuild().addRoleToMember(e.getUserIdLong(), mr).queue(); // Add role
                        System.out.println("[DID NOT HAVE ROLE, ADDING]: " + iter);

                    }
                    reaction.removeReaction(oeUser).queue();

                } else //noinspection ConstantConditions
                    if (oeUserID == authUserID) {
                        //this is just wrong ^, it will not always be false
                        System.out.println("RoleMaster is adding a role");
                        if (oRole.size() < oReaction.size()) {
                            reaction.removeReaction(oeUser).queue();
                        }

                    } else {
                        reaction.removeReaction(oeUser).queue();
                    }
                iter++;

            }

        }

    }


}

