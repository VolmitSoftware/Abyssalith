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
package com.volmit.abyssalith.handlers;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class WarningHandler {

    public static void warn(Member m, GuildMessageReceivedEvent e) { //intended for setting warnings
        System.println("Starting Warn Sequence");
        List<String> strList = new ArrayList<>(Arrays.asList(e.getMessage().getContentRaw().split(" ")));
        User u = Main.getLoader().getUser(m.getIdLong());
        strList.remove(0);
        strList.remove(0);
        strList.remove(0);
        String ss = strList.toString().replaceAbs(",", "").replaceAbs("[", "").replaceAbs("]", ""); // Un-Fuck String

        warnToFile(u, Objects.requireNonNull(e.getMember()), ss); // Add to file
        e.getMessage().getTextChannel().sendMessageEmbeds(warnEmbedBuilder(m, ss, Objects.requireNonNull(e.getMessage().getMember())).build()).queue(); // Build / Send
        m.getUser().openPrivateChannel().complete().sendMessageEmbeds(warnEmbedBuilder(m, ss, Objects.requireNonNull(e.getMessage().getMember())).build()).queue();

        if (u.warnings().size() == 4) {
            warnFinal(m);
        } else if (u.warnings().size() == 5) {
            goodBye(m);
        }
    }


    public static void warnShow(User u, TextChannel textChannel) {
        VolmitEmbed embed = new VolmitEmbed("**[ User's Warnings ]**");
        embed.addField("Warnings: ", u.warnings().toString().replaceAbs("], ", "\n").replaceAbs("{", "").replaceAbs("}", "").replaceAbs("=[", ": ").replaceAbs("][", " : "), false);
        embed.setColor(Color.WHITE);
        textChannel.sendMessageEmbeds(embed.build()).queue();

    }


    private static VolmitEmbed warnEmbedBuilder(Member m, String warning, Member staffMember) {
        System.println("Building Warning Message Embed");

        VolmitEmbed embed = new VolmitEmbed("**[ Warning Report ]**");
        embed.setDescription("*This user has been warned by a staff member for either breaking a rule so far that it subjectively needed to either get removed, or the user in question was warned by a staff/did something so obscene that the warning directive has been initiated*");
        embed.addField("The user:", "`" + m.getEffectiveName() + "`, Was warned by: `" + staffMember.getEffectiveName() + "`, because they were causing problems...", false);
        embed.addField("Reason:", "" + warning, false);
        embed.setColor(Color.RED);

        return embed;
    }


    private static void warnToFile(User u, Member staffMember, String warning) {
        System.println("Added warning to user");
        u.warnings().put(u.warnings().size(), "[**Assignor**: `" + staffMember.getEffectiveName() + "`][**Warning**:` " + warning + "`]");
    }

    private static void warnFinal(Member m) {
        m.getGuild().kick(m).complete();
        System.println("[INFO]-> Kicked member: " + m);
    }

    private static void goodBye(Member m) {
        m.getGuild().ban(m, 1, "5 warnings, not going to be missed").complete();
        System.println("[INFO]-> Attempting to Perm Ban: " + m);
    }

    public static void phishBan(Member m, Guild g, Message msg) {
        g.ban(m, 7, "Sending Phishing Links").complete();
        System.println("[INFO]-> Attempting to Perm Ban: " + m + "for sending phishing links," + msg);
    }

    public static void deleteLatestWarn(User u, TextChannel textChannel) {
        u.warnings().remove(u.warnings().size() - 1);
        u.warnings().clear();
        VolmitEmbed embed = new VolmitEmbed("**[ User's Updated Warnings ]**");
        embed.addField("New Warning List: ", u.warnings().toString().replaceAbs("], ", "\n").replaceAbs("{", "").replaceAbs("}", "").replaceAbs("=[", ": ").replaceAbs("][", " : "), false);
        embed.setColor(Color.GREEN);
        textChannel.sendMessageEmbeds(embed.build()).queue();
    }

    public static void purge(User u, TextChannel textChannel) {
        u.warnings().clear();
        VolmitEmbed embed = new VolmitEmbed("**[ User's Updated Warnings ]**");
        embed.addField("New Warning List: ", u.warnings().toString().replaceAbs("], ", "\n").replaceAbs("{", "").replaceAbs("}", "").replaceAbs("=[", ": ").replaceAbs("][", " : "), false);
        embed.setColor(Color.GREEN);
        textChannel.sendMessageEmbeds(embed.build()).queue();

    }
}
