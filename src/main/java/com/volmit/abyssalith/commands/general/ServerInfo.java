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

import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.commands.SkipCommand;
import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


public class ServerInfo extends VolmitCommand {
    // Constructor
    public ServerInfo() {
        super(
                "info",
                new String[]{"sinfo"},
                new String[]{}, // Always permitted if empty. User must have at least one if specified.
                "Reveals all statistic information from the server",
                false,
                null
        );
    }

    @SkipCommand
    private static class GuildStats {

        private final String name;
        private final String id;
        private final String avatar;
        private final String afk;
        private final String roles;
        private final int textChans;
        private final int threadChans;
        private final int newsChans;
        private final int stageChans;
        private final int voiceChans;
        private final int categories;
        private final int rolesCount;
        private final long all;
        private final long users;
        private final long onlineUsers;
        private final long bots;
        private final long onlineBots;
        private final Member owner;

        private GuildStats(Guild g) {
            Abyss.info("Sending Guild Stats");

            List<Member> l = g.getMembers();

            this.name = g.getName();
            this.id = g.getId();
            this.avatar = g.getIconUrl() == null ? "not set" : g.getIconUrl();
            this.textChans = g.getTextChannels().size();
            this.stageChans = g.getStageChannels().size();
            this.threadChans = g.getThreadChannels().size();
            this.newsChans = g.getNewsChannels().size();
            this.voiceChans = g.getVoiceChannels().size();
            this.categories = g.getCategories().size();
            this.rolesCount = g.getRoles().size();
            this.afk = g.getAfkChannel() == null ? "not set" : g.getAfkChannel().getName();
            this.owner = g.getOwner();

            this.all = l.size();
            this.users = l.stream().filter(m -> !m.getUser().isBot()).count();
            this.onlineUsers = l.stream().filter(m -> !m.getUser().isBot() && !m.getOnlineStatus().equals(OnlineStatus.OFFLINE)).count();
            this.bots = l.stream().filter(m -> m.getUser().isBot()).count();
            this.onlineBots = l.stream().filter(m -> m.getUser().isBot() && !m.getOnlineStatus().equals(OnlineStatus.OFFLINE)).count();

            this.roles = g.getRoles().stream()
                    .filter(r -> !r.getName().contains("everyone"))
                    .map(r -> String.format("%s (`%d`)", r.getName(), getMembsInRole(r)))
                    .collect(Collectors.joining(", "));
        }

        long getMembsInRole(Role r) {
            return r.getGuild().getMembers().stream().filter(m -> m.getRoles().contains(r)).count();
        }
    }

    // Handle
    @Override
    public void handle(List<String> args, MessageReceivedEvent e) {
        Guild g = e.getGuild();

        GuildStats gs = new GuildStats(g);

        String usersText = String.format(
                """
                        **All Clients:**   %d
                        **Members:**   %d   (Online:  %d)
                        """ + "**Bots:**   %d   (Online:  %d)",
                gs.all, gs.users, gs.onlineUsers, gs.bots, gs.onlineBots
        );
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.yellow)
                .setTitle(gs.name + "  -  GUILD STATS")
                .addField("Name:", gs.name, false)
                .addField("ID:", gs.id, false)
                .addField("Owner:", gs.owner.getUser().getName() + "#" + gs.owner.getUser().getDiscriminator(), false)
                .addField("Channels", "**Text Channels:**  " + gs.textChans + "\n**Voice Channels:**  " + gs.voiceChans +  "\n**Stage Channels:**  " + gs.stageChans + "\n**Thread Channels:**  " + gs.threadChans +"\n**News Channels:**  " + gs.newsChans +"\n**Categories:**  " + gs.categories    , false)
                .addField("Members:", usersText, false)
                .addField("Roles (" + gs.rolesCount + "): ", gs.roles, false)
                .addField("AFK Channel", gs.afk, false)
                .addField("Server Avatar", gs.avatar, false);

        if (!gs.avatar.equals("not set"))
            eb.setThumbnail(gs.avatar);
        e.getMessage().getChannel().sendMessageEmbeds(eb.build()).queue();

    }

}