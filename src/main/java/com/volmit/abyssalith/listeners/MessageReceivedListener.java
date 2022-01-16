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

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.data.User;
import com.volmit.abyssalith.handlers.MenuHandler;
import com.volmit.abyssalith.handlers.PermHandler;
import com.volmit.abyssalith.handlers.WarningHandler;
import com.volmit.abyssalith.toolbox.Kit;
import com.volmit.abyssalith.util.XP;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.Objects;
import java.util.Set;

public class MessageReceivedListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e) {
        Abyss.debug("Message Received");

        // XP APPLICATION FOR THE USER ---
        if (!e.getMessage().getAuthor().isBot()) {
            User u = Abyss.getLoader().getUser(e.getMessage().getAuthor().getIdLong()); // USER LOADER
            u.experience(u.experience() + Kit.get().xpPerMessage.rand()); //XP
            u.messagesSent(u.messagesSent() + 1);
            double uxp = u.experience();
            int validator = XP.getLevelForXp(uxp);
            if (validator < Kit.get().xpMaxLevels) {
                roleValidator(e, Kit.get().levelName + XP.getLevelForXp(uxp));
                roleManager(e, Kit.get().levelName + XP.getLevelForXp(uxp), validator);
            }
        }

        //ANTI-GHOST MENTION PORTION ---
        if (e.getMessage().getMentionedMembers().size() > 0) {
            Abyss.debug("User Mention Noticed, and Logged");
            for (Member m : e.getMessage().getMentionedMembers()) {
                User u = Abyss.getLoader().getUser(m.getIdLong());
                if (u.recentMentions().size() > 2) {
                    u.recentMentions().remove(u.recentMentions().size() - 1);
                }
                u.recentMentions().put(u.recentMentions().size(), "[**USER**]" + Objects.requireNonNull(e.getMember()).getEffectiveName() + " [**SAID**]: " + e.getMessage().getContentRaw());
            }
        }

        // REACTIONS FOR BOT TO DELETE ITSELF ---
        if (e.getMessage().getAuthor().isBot()
                && !e.getMessage().getEmbeds().isEmpty()
                && e.getMessage().getActionRows().size() == 0 // Are their no clickable actions
        ) {
            Abyss.debug("Adding Delete Button");
            e.getMessage().addReaction("U+274C").queue();
        }

        //REACTION-ROLE VISTA
        if (!e.getMessage().getAuthor().isBot() // not a bot
                && e.getMessage().getMentionedRoles().size() > 0 // mentioning roles
                && PermHandler.hasAdmin(Objects.requireNonNull(e.getMember())) // Has admin permissions
                && e.getMessage().getContentRaw().contains(Kit.get().reactionRoleString)) { // Contains reaction role string
            if (e.getMessage().getMentionedRoles().size() > 1) {
                MenuHandler.RoleListMenu("rolepage",
                        "Choose your Role(s)!",
                        e.getMessage().getMentionedRoles(),
                        e.getChannel());
            } else {
                e.getChannel().sendMessage("CAn you mention more than 1 role please...").queue();
            }
            e.getMessage().delete().queue();
        }

        //LINGUA PARTITIONER ---
        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().length() > 5 && !e.getMessage().getContentRaw().contains("https:") && Kit.get().useLingua) {
            final LanguageDetector detector = LanguageDetectorBuilder.fromLanguages(
                    Language.ENGLISH, // Default language
                    Language.FRENCH, Language.GERMAN, Language.SPANISH, // Only what i can read or write is here
                    Language.TURKISH, Language.PORTUGUESE, Language.POLISH,
                    Language.KOREAN, Language.DUTCH, Language.CZECH).build();

            if (!detector.detectLanguageOf(e.getMessage().getContentRaw().toLowerCase()).equals(Language.ENGLISH)) {
                //todo: Figure out why this is necessary, and implement a better way to detect/use languages
                Abyss.info("Someone is speaking: " + "" + detector.detectLanguageOf(e.getMessage().getContentRaw().toLowerCase()));
                if (detector.detectLanguageOf(e.getMessage().getContentRaw().toLowerCase()) != Language.UNKNOWN) {
                    Role langRole = e.getGuild().getRolesByName("" + detector.detectLanguageOf(e.getMessage().getContentRaw().toLowerCase()), false).get(0);
                    e.getChannel().sendMessage("Debugging the langRole System! " + langRole.getAsMention()).queue();
                    //todo Actual system here should be something useful
                    Abyss.debug("Language Debug: " + detector.computeLanguageConfidenceValues(e.getMessage().getContentRaw().toLowerCase()));
                }
            }
        }

        //PHISHING DILEMMA
        for (String p : Kit.get().phishing) {
            if (e.getMessage().getContentRaw().toLowerCase().contains(p)) {
                e.getMessage().delete().queue();
                Objects.requireNonNull(e.getMember()).getUser().openPrivateChannel().complete().sendMessage("Hello, You have been banned from volmit for sending Phishing links: `\n" +
                        e.getMessage() +
                        "`\nIf you feel this is a mistake, please send a friend request to: `⋈-NestorPsycho-⋈#0001` and explain the problem, otherwise your account was compromised, or you were unaware of what you were sending \n" +
                        "regardless, you have been banned, until you either fix your account, or handle the situation").queue();
                try {
                    WarningHandler.phishBan(e.getMember(), e.getGuild(), e.getMessage()); // ez ban
                } catch (Exception ignored) {
                } // Don't care about exceptions here, the only possible one is a permission one, and the bot needs to be able to handle it elsewhere
            }
        }

        //PASTEBIN REGISTRAR ---
        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().contains("https://pastebin.com/")) {
            Abyss.info("Initializing  Paste Service");
            String str = e.getMessage().getContentRaw();
            String[] pbArr = str.split(" ");
            for (String s : pbArr) {
                if (s.contains("https://pastebin.com/")) {
                    e.getChannel().sendMessage("Would you like me to scan this for you?: <" + s + ">").queue(f -> {
                        f.editMessageComponents().setActionRow(
                                Button.success("pastbinlinknew", "Yes please!"),
                                Button.danger("no", "No, go away!")
                        ).queue();
                        Abyss.info("Sent Paste Service Buttons");
                    });
                }
            }
        }
        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().contains("https://mclo.gs/")) {
            Abyss.info("Initializing McLogs Service");
            String str = e.getMessage().getContentRaw();
            String[] pbArr = str.split(" ");
            for (String s : pbArr) {
                if (s.contains("https://mclo.gs/")) {
                    e.getChannel().sendMessage("Would you like me to scan this for you?: <" + s + ">").queue(f -> {
                        f.editMessageComponents().setActionRow(
                                Button.success("mcloglinknew", "Yes please!"),
                                Button.danger("no", "No, go away!")
                        ).queue();
                        Abyss.info("Sent McLog Service Buttons");
                    });
                }
            }
        }

        if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().contains("https://hastebin.com/")) {
            Abyss.info("Started the Hastebin Service");
            String str = e.getMessage().getContentRaw();
            String[] pbArr = str.split(" ");
            for (String s : pbArr) {
                if (s.contains("https://hastebin.com/")) {
                    e.getChannel().sendMessage("Would you like me to scan this for you?: <" + s + ">").queue(f -> {
                        f.editMessageComponents().setActionRow(
                                Button.success("hastebinlinknew", "Yes please!"),
                                Button.danger("no", "No, go away!")
                        ).queue();
                        Abyss.info("Sent HasteBin Service Buttons");
                    });
                }
            }
        }

        //ROLE MANAGEMENT FINAL RESULT
        User u = Abyss.getLoader().getUser(Objects.requireNonNull(e.getMember()).getIdLong()); // Load the user object
        Set<String> lRoles = u.roleIds(); // Load the Roles from the user file
        if (lRoles.size() < e.getMember().getRoles().size() && !e.getMember().getUser().isBot() && Kit.get().usePersistentRoles) {
            for (Role r : e.getMember().getRoles()) {
                u.roleIds().add(r.getId());
            }
            Abyss.info("Found Missing Roles, Rebinding to : " + e.getMember().getEffectiveName());
        }
        if (lRoles.size() > e.getMember().getRoles().size() && !e.getMember().getUser().isBot()) {
            for (Role r : e.getMember().getRoles()) {
                u.roleIds().add(r.getId());
            }
            Abyss.warn("ROLE MISMATCH, REFACTORING ROLES IN USER: " + e.getMember().getEffectiveName());
        }
    }


    // REMOVE FOR LATER -0----------------------------------------------------------------------
    private void roleManager(MessageReceivedEvent e, String role, int v) {
        Role r;
        try {
            if (e.getGuild().getRolesByName(role, false).size() == 1 && e.getGuild().getRolesByName(role, false).contains(e.getGuild().getRolesByName(role, true).get(0))) {
                r = e.getGuild().getRolesByName(role, true).get(0);
                if (r != null) {
                    e.getGuild().addRoleToMember(Objects.requireNonNull(e.getMember()).getIdLong(), r).queue();
                    if (v > 0) {
                        for (Role rol : e.getMember().getRoles()) {
                            if (rol.getName().contains(Kit.get().levelName)) {
                                int rint = Integer.parseInt(rol.getName().replace(Kit.get().levelName, ""));
                                if (v > rint) {
                                    e.getGuild().removeRoleFromMember(e.getMember().getId(), e.getGuild().getRolesByName(
                                            Kit.get().levelName + rint, false).get(0)).complete();
                                    Abyss.info("Removed, excessive child roles from user: " + e.getMember().getId());
                                } else if (v != rint) {
                                    e.getGuild().removeRoleFromMember(e.getMember().getId(), e.getGuild().getRolesByName(
                                            Kit.get().levelName + rint, false).get(0)).complete();
                                    Abyss.warn("Removed a role that was not possible to have reached, or something that does not match: " + e.getMember().getId());
                                }

                            }

                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void roleValidator(MessageReceivedEvent e, String role) {
        if (e.getGuild().getRolesByName(role, false).size() < 1) {
            e.getGuild().createRole().setName(role).setMentionable(false).complete();
            Abyss.info("[RV] - New Maximum level created!");
        } else if (e.getGuild().getRolesByName(role, false).size() > 1) {
            Abyss.warn("For some reason there are too many roles here im having a stroke... Managing...");
            int i = 0;
            for (Role r : e.getGuild().getRolesByName(role, false)) {
                if (i != 0) {
                    r.delete().complete();
                }
                i++;
            }
        }
    }
}
