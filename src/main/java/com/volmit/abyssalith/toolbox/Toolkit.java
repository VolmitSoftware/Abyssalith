package com.volmit.abyssalith.toolbox;

import art.arcane.quill.cache.AtomicCache;
import art.arcane.quill.execution.J;
import art.arcane.quill.io.FileWatcher;
import art.arcane.quill.io.IO;
import art.arcane.quill.json.JSONObject;
import art.arcane.quill.logging.L;
import com.google.gson.Gson;
import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.util.Range;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;

public class Toolkit extends ListenerAdapter {
    // Set from config
    public String BotCompany = "NextdoorSoftworks";
    public String BotGIF = "";
    public String BotColor = "0x000000";
    public String BotToken = "";
    public String BotOwnerID = "";
    public String BotPrefix = "";
    public String LevelName = "Level ";
    public String MoneyName = "";
    public String MoneyEmoji = ":coin:";
    public String ReactionRoleString = "";
    public String RoleBanished = "";
    public String RoleModerator = "";
    public String RoleAdministrator = "";
    public boolean UseBanishedInsteadOfKick = true; // TODO implement a warning / penalty system
    public boolean UseLingua = true;
    public boolean UsePersistentRoles = true;
    public Range XpPerMessage = Range.jitter(0.9f, 0.2f);
    public int XpMaxLevels = 50; // Max roles that can be made by this bot (Level)
    public double XpBaseMultiplier = 1.25f;

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
}
