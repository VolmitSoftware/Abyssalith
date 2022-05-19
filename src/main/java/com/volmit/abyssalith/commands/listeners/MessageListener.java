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
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MessageListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e) {
        //nullchecks for bots now ig or webhooks to be more specific (ie: discord/Git bit causing a no-id Error)
        if (e.getMember() != null) {


            // XP APPLICATION FOR THE USER ---
            if (!e.getMessage().getAuthor().isBot()) {
//            Abyss.debug("XP Check");
                User u = Abyss.getLoader().getUser(e.getMessage().getAuthor().getIdLong()); // USER LOADER
                u.experience(u.experience() + Kit.get().xpPerMessage.rand()); //XP
                u.messagesSent(u.messagesSent() + 1);
                double uxp = u.experience();
                int validator = XP.getLevelForXp(uxp);
                if (validator < Kit.get().xpMaxLevels) {
                    if (Kit.get().useRoleSystem) {
                        roleValidator(e, Kit.get().levelName + XP.getLevelForXp(uxp));
                        roleManager(e, Kit.get().levelName + XP.getLevelForXp(uxp), validator);
                    }
                }
            }

            //ANTI-GHOST MENTION PORTION ---
            if (e.getMessage().getMentions().getMembers().size() > 0) {
                Abyss.debug("User Mention Noticed, and Logged");
                for (Member m : e.getMessage().getMentions().getMembers()) {
                    User u = Abyss.getLoader().getUser(m.getIdLong());
                    if (u.recentMentions().size() > 2) {
                        u.recentMentions().remove(u.recentMentions().size() - 1);
                    }
                    u.recentMentions().put(u.recentMentions().size(), "[**USER**]" + Objects.requireNonNull(e.getMember()).getEffectiveName() + " [**SAID**]: " + e.getMessage().getContentRaw());
                }
            }

            // REACTIONS FOR BOT TO DELETE ITSELF ---
            if (e.getMessage().getAuthor().getIdLong() == e.getJDA().getSelfUser().getIdLong()
                    && !e.getMessage().getEmbeds().isEmpty()
                    && e.getMessage().getActionRows().size() == 0 // Are their no clickable actions
            ) {
                Abyss.debug("Adding Delete Button");
                e.getMessage().addReaction("U+274C").queue();
            }

            //REACTION-ROLE VISTA
            if (!e.getMessage().getAuthor().isBot() // not a bot
                    && e.getMessage().getMentions().getRoles().size() > 0 // mentioning roles
                    && PermHandler.hasAdmin(Objects.requireNonNull(e.getMember())) // Has admin permissions
                    && e.getMessage().getContentRaw().contains(Kit.get().reactionRoleString)) { // Contains reaction role string
                Abyss.debug("ReactionRoles Instanced");
                if (e.getMessage().getMentions().getRoles().size() > 1) {
                    MenuHandler.RoleListMenu("rolepage",
                            "Choose your Role(s)!",
                            e.getMessage().getMentions().getRoles(),
                            e.getChannel());
                } else {
                    e.getChannel().sendMessage("CAn you mention more than 1 role please...").queue();
                }
                e.getMessage().delete().queue();
            }

            //LINGUA PARTITIONER ---
            if (!e.getMessage().getAuthor().isBot() && e.getMessage().getContentRaw().length() > 5 && !e.getMessage().getContentRaw().contains("https:") && Kit.get().useLingua) {
                Abyss.debug("Lingua Check");
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
                    Abyss.debug("Phishing Check");
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
            if (Kit.get().usePasteService) {
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
            }

            //ROLE MANAGEMENT FINAL RESULT
            User u = Abyss.getLoader().getUser(e.getMember().getIdLong()); // Load the user object
            Set<String> fRoles = u.roleIds(); // Load the Roles from the user file
            Set<String> sRoles = new HashSet<>(); // Load the Roles from the server
            for (Role r : e.getMember().getRoles()) {
                sRoles.add(r.getId());
            }
            if (fRoles.containsAll(sRoles) && sRoles.containsAll(fRoles)) {
                //same server, same roles, all is well. Do nothing. but ill add something here later
            } else {
                if (!fRoles.equals(sRoles) && sRoles.size() != 0) { // They have roles, that don't match what they have on file
                    u.roleIds(sRoles);
                    Abyss.info("Roles on user, dont match file. Rebinding file to match: " + e.getMember().getEffectiveName() + "' Server roles");
                } else if (!fRoles.equals(sRoles) && sRoles.size() == 0 && Kit.get().usePersistentRoles) { //If they have Roles on file, and zero roles on the server
                    boolean shr = true;
                    for (String fr : fRoles) {
                        if (e.getGuild().getRoleById(fr) == null) {
                            shr = false;
                        }
                    }
                    if (shr) {
                        Abyss.info("Reapplying PersistentRoles to: " + e.getMember().getEffectiveName());
                        for (String f : fRoles) {
                            if (e.getMessage().getGuild().getRoleById(f) != null) {
                                e.getMessage().getGuild().addRoleToMember(e.getMember(), Objects.requireNonNull(e.getMessage().getGuild().getRoleById(f))).complete();
                            }
                        }
                    } else {
                        u.roleIds(sRoles);
                        Abyss.info("Server Role ReSync: " + e.getMember().getEffectiveName());
                    }

                }

            }
        }
    }


    // REMOVE FOR LATER -0----------------------------------------------------------------------
    private void roleManager(MessageReceivedEvent e, String role, int v) {
        Role r;
        try {
            if (e.getGuild().getRolesByName(role, false).size() == 1 && e.getGuild().getRolesByName(role, false).contains(e.getGuild().getRolesByName(role, true).get(0))) {
                r = e.getGuild().getRolesByName(role, true).get(0);
                if (r != null && e.getMember() != null) {
                    e.getGuild().addRoleToMember(UserSnowflake.fromId(e.getMember().getIdLong()), r).queue();
                    if (v > 0) {
                        for (Role rol : e.getMember().getRoles()) {
                            if (rol.getName().contains(Kit.get().levelName)) {
                                int rint = Integer.parseInt(rol.getName().replace(Kit.get().levelName, ""));
                                if (v > rint) {
                                    e.getGuild().removeRoleFromMember(UserSnowflake.fromId(e.getMember().getId()), e.getGuild().getRolesByName(
                                            Kit.get().levelName + rint, false).get(0)).complete();
                                    Abyss.info("Removed, excessive child roles from user: " + e.getMember().getId());
                                } else if (v != rint) {
                                    e.getGuild().removeRoleFromMember(UserSnowflake.fromId(e.getMember().getId()), e.getGuild().getRolesByName(
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
