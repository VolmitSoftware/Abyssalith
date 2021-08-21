package com.volmit;

import art.arcane.quill.execution.Looper;
import com.volmit.commands.*;
import com.volmit.io.DataLoader;
import com.volmit.io.storage.FileSystemStorageAccess;
import com.volmit.listeners.AutoWiki;
import com.volmit.listeners.OwOListener;
import com.volmit.listeners.Prefix;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.volmit.commands.Shutdown;
import volmbot.commands.*;
import com.volmit.toolbox.Toolkit;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.Objects;


public class Main extends ListenerAdapter {
    public static final IBotProvider provider = new BotProvider();

   @Getter
    private static final DataLoader loader = createLoader();

    private static DataLoader createLoader() {
        return new DataLoader(new FileSystemStorageAccess(new File("Data")));
    }

    // BOT BUILDER BOYS
    public static JDA getJDA() {
        return provider.get().getJDA();
    }

    public static void main(String[] args) throws LoginException {
        org.slf4j.simple.SimpleServiceProvider.class.getSimpleName();
        // Status
        System.out.println("Initializing");


        Toolkit.get().botID = getJDA().getSelfUser().getIdLong();
        Toolkit.get().botUser = getJDA().getUserById(Toolkit.get().botID);
        Toolkit.get().botName = Objects.requireNonNull(Toolkit.get().botUser).getName();

        /// Listener Registrar
        // Log incoming messages
        getJDA().addEventListener(new Main());

        // Listeners
        getJDA().addEventListener(new Toolkit());
        getJDA().addEventListener(new OwOListener());
        getJDA().addEventListener(new AutoWiki());
        getJDA().addEventListener(new Prefix());
        // Money Commands
        getJDA().addEventListener(new Eco());

        // Commands
        getJDA().addEventListener(new Links());
        getJDA().addEventListener(new Stats());
        getJDA().addEventListener(new Ping());
        getJDA().addEventListener(new Poll());
        getJDA().addEventListener(new Log());
        getJDA().addEventListener(new Paste());
        getJDA().addEventListener(new Shutdown());
        getJDA().addEventListener(new Commands(getJDA())); // This one MUST be last

        new Looper() {
            @Override
            protected long loop() {
                Toolkit.tick();
                return 1000;
            }
        }.start();

        Runtime.getRuntime().addShutdownHook(new Thread(loader::close));
    }

    @Override
    public void onReady(@NonNull ReadyEvent e) {
        System.out.println(e.getJDA().getSelfUser().getAsTag() + "IS WATCHING THE UNIVERSE");
        System.out.println("[ BOT HAS STARTED! ]");
    }

    public static void shutdown() {
        getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
        getJDA().shutdown();
        System.exit(1);
    }

    // LOGGERS
    private static void log(String tag, Object t)
    {
        System.out.println("[" + tag + "]: " + t);
    }
    public static void warn(Object message) {
        log("WARN", message);
    }
    public static void info(Object message) {
        log("INFO", message);
    }
    public static void error(Object message) {
        log("ERROR", message);
    }
    public static void debug(Object message) {
        log("DEBUG", message);
    }
}

