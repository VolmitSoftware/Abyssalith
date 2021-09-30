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

import art.arcane.quill.collections.KList;
import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.util.VolmitEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class PasteHandler extends ListenerAdapter {

    private static final KList<Definition> definitions = new KList<>(
            new Definition("[Iris]: Couldn't find Object:", "Objects are Broken!", "- Iris cant find certain objects in your pack"),
            new Definition("Ambiguous plugin name", "Duplicate Plugin Names", "- You have duplicate plugins on the server, this causes startup issues"),
            new Definition("Couldn't read Biome file:", "You have a typo in a Biome file", "- There is a typo in one of the files in your pack folder!"),
            new Definition("[Iris]: Unknown Block Data:", "Unknown Block Data", "- Iris cant find block data (nbt mapping issue) "),
            new Definition("IT IS HIGHLY RECOMMENDED YOU RESTART THE SERVER BEFORE GENERATING!", "Restart your server", "- Iris needs to restart the server for the datapacks to work properly"),
            new Definition("[Multiverse-Core]", "Using Multiverse", "- You are using multiverse, are you using that for an iris world?"),
            new Definition("DO NOT REPORT THIS TO PAPER - THIS IS NOT A BUG OR A CRASH", "Paper Watchdog Spam", "**PLEASE turn off the paper spam!** \n https://docs.volmit.com/iris/plugin/faq"),
            new Definition(s -> !s.contains("[Iris] Enabling Iris"), "Iris not installed / not a full log", "This does not contain a **full** log with Iris installed, perhaps try again if you want more information.")

    );

    @Override
    public void onButtonClick(ButtonClickEvent e) { //TODO--------------THIS  IS THE BUTTON MANAGER---------------------//

        if (e.getComponentId().equals("no")) {
            Objects.requireNonNull(e.getMessage()).delete().queue();
        } else if (e.getComponentId().equals("hastebinlinknew") || e.getComponentId().equals("pastbinlinknew") || e.getComponentId().equals("mcloglinknew")) {


            i("Starting Interpreter Paste Service");
            String properURL = null;
            Document doc;
            if (Objects.requireNonNull(e.getMessage()).getContentRaw().contains("https://pastebin.com")) {
                i("Reached Pasebin");
                String[] args = e.getMessage().getContentRaw().replaceAll(">", "").split("/", 5);
                Main.info(args[3]);
                String stem = args[3];
                properURL = "https://pastebin.com/raw/" + stem;
            } else if (Objects.requireNonNull(e.getMessage()).getContentRaw().contains("https://mclo.gs/")) {
                i("Reached McLogs");
                String[] args = e.getMessage().getContentRaw().replaceAll(">", "").split("/", 5);
                Main.info(args[3]);
                String stem = args[3];
                properURL = "https://api.mclo.gs/1/raw/" + stem;
            } else if (Objects.requireNonNull(e.getMessage()).getContentRaw().contains("https://hastebin.com/")) {
                i("Reached Hastebin");
                String[] args = e.getMessage().getContentRaw().replaceAll(">", "").split("/", 5);
                Main.info(args[3]);
                String stem = args[3];
                properURL = "https://hastebin.com/raw/" + stem;
            }

            try {
                URL url = new URL(properURL);
                doc = Jsoup.parse(url.openStream(), "UTF-8", url.toString()); // Get Document object ('url' is a java.net.URL object)
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }

            VolmitEmbed embed = new VolmitEmbed("Automated Error Detector", e.getMessage());
            embed.setTitle("Automated Detriment Detector");
            embed.setDescription("Hello user! This is A.D.D. and I will do my best to read your file!\n" + "||Paste: " + properURL + "||");
            Main.info("PROCESSING PASTEBIN FILE FROM " + properURL);
            int problems = test(doc.text(), embed);


            // NO PROBLEMS
            if (problems == 0) {
                embed.addField("Well, This is not good.", "I cant seem to figure anything out; try asking the support team about the issue!", false);
            }

            embed.send(e.getMessage(), true, 1000);
        }
    }

    /**
     * Test a text and add help fields to an embed
     *
     * @param text  Text
     * @param embed Embed
     * @return Amount of problem definitions that matched
     */
    private static int test(String text, VolmitEmbed embed) {
        AtomicInteger problems = new AtomicInteger();

        definitions.forEach(definition -> {
            if (definition.appliesOn(text)) {
                embed.addField(definition.getField());
                problems.getAndIncrement();
            }
        });

        return problems.get();
    }

    private static class Definition {
        private final boolean fullWidth;
        private final String helpBody;
        private final String helpTitle;
        private final Predicate<String> predicate;

        /**
         * A problem definition
         *
         * @param trueIfContains If text contains this, #appliesOn will return true
         * @param title          The title of the help field
         * @param body           The body of the help field
         */
        public Definition(String trueIfContains, String title, String body) {
            this(s -> s.contains(trueIfContains), title, body);
        }

        /**
         * A problem definition
         *
         * @param whenTrue The predicate on a string that returns true when the definition applies
         * @param title    The title of the help field
         * @param body     The body of the help field
         */
        public Definition(Predicate<String> whenTrue, String title, String body) {
            this(whenTrue, title, body, false);
        }

        /**
         * A problem definition
         *
         * @param whenTrue  The predicate on a string that returns true when the definition applies
         * @param title     The title of the help field
         * @param body      The body of the help field
         * @param fullWidth Whether the full line-width in the embed is required
         */
        public Definition(Predicate<String> whenTrue, String title, String body, boolean fullWidth) {
            this.predicate = whenTrue;
            this.helpTitle = title;
            this.helpBody = body;
            this.fullWidth = fullWidth;
        }

        /**
         * Get a message embed help-field from this definition
         *
         * @return A message field
         */
        public MessageEmbed.Field getField() {
            return new MessageEmbed.Field(helpTitle, helpBody, fullWidth, true);
        }

        /**
         * Whether this problem definition applies to
         *
         * @param text A text
         * @return True if applies, false if not
         */
        public boolean appliesOn(String text) {
            return predicate.test(text);
        }
    }
}
