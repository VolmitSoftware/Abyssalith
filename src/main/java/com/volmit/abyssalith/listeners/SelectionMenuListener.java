/*
 * Abyssalith is a Discord Bot for Volmit Software's Community
 * Copyright (c) 2021 VolmitSoftware (Arcane Arts)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.volmit.abyssalith.listeners;

import com.volmit.abyssalith.Abyss;
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
        Abyss.debug("Menu Selection Recorded");
        //captures selection menu, and applies roles/removes roles from the user
        if (e.getComponentId().equalsIgnoreCase("menu:rolepage")) {
            Abyss.info("Subjugating selection Menu");
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