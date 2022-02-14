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
package com.volmit.abyssalith.commands.listeners;

import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.handlers.RoleHandler;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SelectMenuListener extends ListenerAdapter {
    public void onSelectMenuInteraction(SelectMenuInteractionEvent e) {
        Abyss.debug("Menu Selection Recorded");
        if (e.getMember() != null) {
            if (e.getComponentId().equalsIgnoreCase("menu:rolepage")) {
                Abyss.info("Subjugating selection Menu");

                Set<String> roles = new HashSet<>(); // Null Set of Roles
                List<SelectOption> sel = e.getSelectedOptions(); // Selected Options
                List<SelectOption> other = e.getComponent().getOptions(); // All  Options

                for (SelectOption S : other) {
                    RoleHandler.removeRole(e.getMember(), S.getLabel());
                }

                for (SelectOption S : sel) {
                    RoleHandler.addRole(e.getMember(), S.getLabel());
                    roles.add(S.getLabel());
                }

                e.reply("Your new roles are:" + roles).setEphemeral(true).queue();
            } else {
                e.reply("Please contact an administrator, i cant see any roles!").setEphemeral(true).queue();
            }
        }
    }
}