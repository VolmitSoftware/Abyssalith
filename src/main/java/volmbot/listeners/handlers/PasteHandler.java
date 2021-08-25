package volmbot.listeners.handlers;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import volmbot.Main;
import volmbot.util.VolmitEmbed;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class PasteHandler extends ListenerAdapter {

    @Override
    public void onButtonClick(ButtonClickEvent e) { //TODO--------------THIS  IS THE BUTTON MANAGER---------------------//

        if (e.getComponentId().equals("pastbinlinknew")) {

            String[] args = e.getMessage().getContentRaw().split("/", 10);
            System.out.println(args[3]);


            String stem = args[3];
            String properURL = "https://pastebin.com/raw/" + stem;
            Document doc;

            try {
                URL url = new URL(properURL);
                doc = Jsoup.parse(url.openStream(), "UTF-8", url.toString()); // Get Document object ('url' is a java.net.URL object)
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }

            VolmitEmbed embed = new VolmitEmbed("Automated Error Detector", e.getMessage());
            embed.setTitle("Automated Detriment Detector");
            embed.setDescription("Hello user! This is A.D.D. and ill do my best to read your file!\n" + "||" + properURL + "||");
            Main.info("PROCESSING PASTEBIN FILE");
            int prob = 0;

            // Shitty loop for stuff
            if (doc.text().contains("[Iris]: Couldn't find Object:")) { // Use element.text() to get the text of the element as a String
                embed.addField("Objects are Broken!: ", "- Iris cant find certain objects in your pack", false);
                prob++;
            }
            if (doc.text().contains("Couldn't read Biome file:")) { // Use element.text() to get the text of the element as a String
                embed.addField("You have a typo in a Biome file: ", "- There is a typo in one of the files in your pack folder!", false);
                prob++;
            }
            if (doc.text().contains("[Iris]: Unknown Block Data:")) { // Use element.text() to get the text of the element as a String
                embed.addField("Unknown Block Data ", "- Iris cant find block data (nbt mapping issue) ", false);
                prob++;
            }
            if (doc.text().contains("IT IS HIGHLY RECOMMENDED YOU RESTART THE SERVER BEFORE GENERATING!")) { // Use element.text() to get the text of the element as a String
                embed.addField("Restart your server", "- Iris needs to restart the server for the datapacks to work properly", false);
                prob++;
            }
            if (doc.text().contains("[Multiverse-Core]")) { // Use element.text() to get the text of the element as a String
                embed.addField("Using Multiverse ", "- You are using multiverse, are you using that for an iris world?", false);
                prob++;
            }
            if (doc.text().contains("DO NOT REPORT THIS TO PAPER - THIS IS NOT A BUG OR A CRASH")) { // Use element.text() to get the text of the element as a String
                embed.addField("Paper Watchdog Spam", "**PLEASE turn off the paper spam!** \n https://docs.volmit.com/iris/plugin/faq", false);
                prob++;
            }
            if (!doc.text().contains("[Iris] Enabling Iris")) {
                embed.addField("This does not contain a **full** log with Iris installed, perhaps try again if you want more information.", "", false);
            }

            // NO PROBLEMS
            if (prob < 1) {
                embed.addField("Well, This is not good.", "I cant seem to figure anything out; try asking the support team about the issue!", false);
            }

            embed.send(e.getMessage(), true, 1000);


        } else if (e.getComponentId().equals("no")) {
            Objects.requireNonNull(e.getMessage()).delete().queue();

        }
    }

}
