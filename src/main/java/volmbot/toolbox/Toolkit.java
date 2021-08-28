package volmbot.toolbox;

import art.arcane.quill.cache.AtomicCache;
import art.arcane.quill.execution.J;
import art.arcane.quill.io.FileWatcher;
import art.arcane.quill.io.IO;
import art.arcane.quill.json.JSONObject;
import art.arcane.quill.logging.L;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import volmbot.Main;
import volmbot.util.Range;

import java.io.File;
import java.io.IOException;

public class Toolkit extends ListenerAdapter {
    // Set from config
    public String ModRole = "";//Leave blank change in config
    public String AdminRole = "";//Leave blank change in config
    public String MoneyName = "";//Leave blank change in config
    public String MoneyEmoji = ":coin:";
    public String Company = "NextdoorSoftworks";
    public String BotGIF = "";//Leave blank change in config
    public String BotColor = "0x000000";
    public String BotToken = "";//Leave blank change in config
    public Range MsgXp = Range.jitter(0.9f, 0.2f);
    public String RoleString = "";//Leave blank change in config
    public String BotOwnerID = "";//Leave blank change in config
    public String BotPrefix = "";//Leave blank change in config
    public String[] ExperienceRoles = new String[] {"", "", ""};
    public String ExperienceRolesColor = "0x000000";
    public double BaseXpLevel = 500f;//Leave blank change in config
    public double BaseXpMultiplier = 1.25f;//Leave blank change in config


    // Set from main class
    public transient Long botID;
    public transient User botUser;
    public transient String botName;

    // Used for hot-loading and config
    private static final FileWatcher fw = new FileWatcher(getFile());
    private static AtomicCache<Toolkit> instance = new AtomicCache<>();

    public void save() {
        File file = getFile();
        file.getParentFile().mkdirs();

        J.attempt(() -> IO.writeAll(file, new JSONObject(new Gson().toJson(this)).toString(4)));
    }

    public static void tick() {
        if (fw.checkModified()) {

            instance = new AtomicCache<>();
            L.v("Hot-loaded Config");
            Main.getJDA();
        }
    }

    public static Toolkit get() {
        return instance.aquire(() -> {
            File f = getFile();
            System.out.println(f.getAbsolutePath());
            f.getParentFile().mkdirs();
            Toolkit dummy = new Toolkit();

            if (!f.exists()) {
                dummy.save();
            }
            try {
                Toolkit tk = new Gson().fromJson(IO.readAll(f), Toolkit.class);
                System.out.println(tk.BotToken);
                tk.save();
                fw.checkModified();
                return tk;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dummy;
        });
    }

    private static File getFile() {
        return new File("config/toolkit.json");
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (!e.getMessage().getAuthor().isBot()) {
            get();
        }
    }
}
