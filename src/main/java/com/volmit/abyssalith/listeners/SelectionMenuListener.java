package com.volmit.abyssalith.listeners;

import com.volmit.abyssalith.handlers.RoleHandler;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import java.util.List;
import java.util.Objects;

public class SelectionMenuListener extends ListenerAdapter {
    public void onSelectionMenu(SelectionMenuEvent e) {
        i("Subjugating selection Menu");
        i(Objects.requireNonNull(e.getComponent()).getOptions().get(0).getLabel());
        i(Objects.requireNonNull(e.getSelectedOptions()).get(0).getLabel());

        List<SelectOption> sel = e.getSelectedOptions(); //all of the selections that were CHOSEN
        List<SelectOption> other = e.getComponent().getOptions(); // ALL OTHER OPTIONS
        other.removeAll(sel);

        for (SelectOption S : sel) {  //THIS ADDS ALL ROLES FROM THE LIST THAT THE USER SELECTED
            RoleHandler.addRole(Objects.requireNonNull(e.getMember()), S.getLabel());
        }

        for (SelectOption S : other) {  //THIS REMOVES ALL ROLED FROM THE LIST THAT THE USER MAY HAVE
            RoleHandler.removeRole(Objects.requireNonNull(e.getMember()), S.getLabel());
        }

    }
}