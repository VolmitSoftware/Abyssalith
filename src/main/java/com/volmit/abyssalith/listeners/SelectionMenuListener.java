package com.volmit.abyssalith.listeners;

import com.volmit.abyssalith.handlers.RoleHandler;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SelectionMenuListener extends ListenerAdapter {
    public void onSelectionMenu(SelectionMenuEvent e) {
        //captures selection menu, and applies roles/removes roles from the user

        if (e.getComponentId().equalsIgnoreCase("menu:rolepage")) {
            i("Subjugating selection Menu");
            e.getComponentId();

            Set<String> roles = new HashSet<>();
            List<SelectOption> sel = e.getSelectedOptions(); //all of the selections that were CHOSEN
            List<SelectOption> other = Objects.requireNonNull(e.getComponent()).getOptions(); // ALL OTHER OPTIONS
            other.removeAll(Objects.requireNonNull(sel));

            for (SelectOption S : sel) {  //THIS ADDS ALL ROLES FROM THE LIST THAT THE USER SELECTED
                RoleHandler.addRole(Objects.requireNonNull(e.getMember()), S.getLabel());
                roles.add(S.getLabel());
            }
            for (SelectOption S : other) {  //THIS REMOVES ALL ROLED FROM THE LIST THAT THE USER MAY HAVE
                RoleHandler.removeRole(Objects.requireNonNull(e.getMember()), S.getLabel());
            }

            e.reply("Your new roles are:" + roles.toString()).setEphemeral(true).queue();
        } else {
            e.reply("Please contact an administrator, i cant see any roles!").setEphemeral(true).queue();
        }
    }
}