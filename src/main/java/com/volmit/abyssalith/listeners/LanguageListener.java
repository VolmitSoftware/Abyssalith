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
import com.volmit.abyssalith.toolbox.Toolkit;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LanguageListener extends ListenerAdapter {
    @SneakyThrows
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getMessage().imUser() && e.getMessage().getContentRaw().length() > 7 && !e.getMessage().getContentRaw().contains("https:") && Toolkit.get().UseLingua) {
            final LanguageDetector detector = LanguageDetectorBuilder.fromLanguages(
                    Language.ENGLISH, Language.FRENCH, Language.GERMAN, Language.SPANISH, // Only what i can read or write is here
                    Language.TURKISH, Language.PORTUGUESE, Language.POLISH, Language.KOREAN,
                    Language.JAPANESE, Language.DUTCH, Language.CZECH).build();

            if (!detector.detectLanguageOf(e.getMessage().lower()).equals(Language.ENGLISH)) {
                //todo: Figure out why this is necessary, and implement a better way to detect/use languages
                i("Someone is speaking: " + "" + detector.detectLanguageOf(e.getMessage().lower()));
                d("Language Debug: " + detector.computeLanguageConfidenceValues(e.getMessage().lower()));
            }
        }
    }
}
