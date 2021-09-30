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

package com.volmit.abyssalith.commands.general;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.handlers.WarningHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.VolmitCommand;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Objects;

import static art.arcane.amulet.MagicalSugar.*;

public class Warn extends VolmitCommand {
    // Constructor
    public Warn() {
        super(
                "warn",
                new String[]{"warn", "strike", "wn"},
                new String[]{Kit.get().RoleModerator, Kit.get().RoleAdministrator}, // Always permitted if empty. User must have at least one if specified.
                "This command Applies Warnings to the person mentioned, or prints them",
                true,
                "warn @psycho being a bad developer\n or \n.warn show <userid>>"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        Member m = e.getMessage().getMentionedMembers().get(0);

        if (e.getMessage().getMentionedRoles().size() == 0 && !m.getUser().equals(Main.getJDA().getSelfUser())) {


            int as = args.size() - 1;
            StringBuilder s = new StringBuilder();
            VolmitEmbed embed = new VolmitEmbed("⚠**Warnings**⚠", e.getMessage());
            User u;


            if (e.getMessage().getMentionedMembers().size() > 0) {
                u = Main.getLoader().getUser(e.getMessage().getMentionedMembers().get(0).getIdLong()); // grab the @current user
            } else {
                u = Main.getLoader().getUser(Long.parseLong(args[2])); // only if you are showing the warns, does not work otherwise.
            }

            if (Objects.equals(args[1], "show") && args.size() == 3) {
                if (u.warnings().size() == 0) {
                    embed.setDescription("No Warnings for this user!");
                    embed.addField("Nothing to see here!", "really... im serious, they are actually a good person, so far...", false);

                } else {
                    embed.setDescription("These are the warnings in order from first to last for the user");
                    for (String str : u.warnings().values()) {
                        embed.addField("Warning- ", str, false);
                    }
                }
            } else {
                int wNum = u.warnings().size() + 1;
                if (args.size() > 2) {
                    for (Integer arg : 2to as) {
                        s.append(args[arg]).append(" ");
                    }

                    embed.addField("You have been Warned", "Id advise you stop whatever it is you did", false);
                    embed.addField("Reason", "" + s, false);
                    u.warnings().put(wNum, "" + s + " \n[Assigned by: " + Objects.requireNonNull(e.getMember()).getEffectiveName() + "]");
                    WarningHandler.warn(Objects.requireNonNull(e.getGuild().getMemberById(u.id())), e.getGuild());

                } else if (args.size() == 2) {
                    s.append("No Reason Given");
                    System.println(s);
                    u.warnings().put(wNum, "" + s + " \n[Assigned by: " + Objects.requireNonNull(e.getMember()).getEffectiveName() + "]");
                    embed.addField("You have been Warned", "Id advise you stop whatever it is you did", false);
                    WarningHandler.warn(Objects.requireNonNull(e.getGuild().getMemberById(u.id())), e.getGuild());
                }
            }
            embed.send(e.getMessage(), false, 1000);
            e.getMessage().delete().queue();

        }
    }
}