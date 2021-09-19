package volmbot.listeners;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import volmbot.toolbox.Toolkit;

public class LanguageListener extends ListenerAdapter {
    @SneakyThrows
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getMessage().imUser() && e.getMessage().getContentRaw().length() > 7 && !e.getMessage().getContentRaw().contains("https:") && Toolkit.get().UseLingua) {
            final LanguageDetector detector = LanguageDetectorBuilder.fromLanguages(
                    Language.ENGLISH, Language.FRENCH, Language.GERMAN, Language.SPANISH, // Only what i can read or write is here
                    Language.TURKISH, Language.PORTUGUESE, Language.POLISH, Language.KOREAN,
                    Language.JAPANESE, Language.DUTCH, Language.CZECH).build();

            if (!detector.detectLanguageOf(e.getMessage().lower()).equals(Language.ENGLISH)) {
                i("Someone is speaking: "+  "" + detector.detectLanguageOf(e.getMessage().lower()));
                d("Language Debug: "+ detector.computeLanguageConfidenceValues(e.getMessage().lower()));
            }
        }
    }
}
