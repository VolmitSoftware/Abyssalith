package volmbot.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import volmbot.Main;
import volmbot.util.VolmitCommand;
import volmbot.util.VolmitEmbed;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class Paste extends VolmitCommand {
    // Constructor
    public Paste() {
        super(
                "paste",
                new String[]{"paste","pastebin","pb"},
                new String[]{}, // Add role name here. Empty: always / 1+: at least one.
                "Analyses a log file with for some common errors",
                true,
                "log <pastebin link>"
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {
        String stem = args.get(1).replace("https://pastebincom/", "");
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
        embed.setDescription("Hello user! This is automated, and ill do my best!**\n Here is what I'm using for reference: " + "||" + properURL + "||");
        Main.info("PROCESSING PASTEBIN FILE");
        int prob = 0;

        // Shitty loop for stuff
        if (doc.text().contains("[Iris]: Couldn't find Object:")) { // Use element.text() to get the text of the element as a String
            embed.addField("Objects are Broken!: ", "- Iris cant find certain objects, is this intended?", false);
            prob++;
        }
        if (doc.text().contains("Couldn't read Biome file:")) { // Use element.text() to get the text of the element as a String
            embed.addField("You have a typo in a Biome file: ", "- There is a typo in one of the files in your pack folder!", false);
            prob++;
        }
        if (doc.text().contains("[Iris]: Failed to generate parallax")) { // Use element.text() to get the text of the element as a String
            embed.addField("Iris's Parallax Layer has failed: ", "- Chunks are not generating to a Paralax bug! please report this to Support, and make it an issue on the Issue Tracker, See the channel description for that link", false);
            prob++;
        }
        if (doc.text().contains("configured to generate Overworld!")) { // Use element.text() to get the text of the element as a String
            embed.addField("Iris is being used in the Bukkit.yml file: ", "- The default chunk generator is being used in the Bukkit.Yml file (not a bug, just an observation)", false);
            prob++;
        }
        if (doc.text().contains("Failed to insert parallax at chunk")) { // Use element.text() to get the text of the element as a String
            embed.addField("Iris's Parallax Layer generation: ", "- The Parallax generator is breaking trying to generate a chunk, perhaps check your configuration files", false);
            prob++;
        }
        if (doc.text().contains("and may increase memory usage!")) { // Use element.text() to get the text of the element as a String
            embed.addField("Large objects are in use", "- The server is using Supermassive trees (not a bug, just a notice)- if these objects are broken, please report that!\n **How much memory are you using?**" , false);
            prob++;
        }
        if (doc.text().contains("DO NOT REPORT THIS TO PAPER - THIS IS NOT A BUG OR A CRASH")) { // Use element.text() to get the text of the element as a String
            embed.addField("Paper Watchdog Spam", "PLEASE turn off the paper spam! Go Here: \n https://docs.volmit.com/iris/plugin/faq", false);
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
    }
}




