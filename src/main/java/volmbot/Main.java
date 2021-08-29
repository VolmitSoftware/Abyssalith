package volmbot;

import art.arcane.quill.execution.J;
import art.arcane.quill.execution.Looper;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import volmbot.commands.Shutdown;
import volmbot.commands.*;
import volmbot.io.DataLoader;
import volmbot.io.storage.FileSystemStorageAccess;
import volmbot.listeners.*;
import volmbot.listeners.handlers.ButtonHandler;
import volmbot.listeners.handlers.PasteHandler;
import volmbot.toolbox.Toolkit;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Main extends ListenerAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.Listener.class);
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
        LOGGER.info("Initializing");


        Toolkit.get().botID = getJDA().getSelfUser().getIdLong();
        Toolkit.get().botUser = getJDA().getUserById(Toolkit.get().botID);
        Toolkit.get().botName = Objects.requireNonNull(Toolkit.get().botUser).getName();

        // Log incoming messages
        getJDA().addEventListener(new Main());
        // Listeners
        getJDA().addEventListener(new Toolkit());
        getJDA().addEventListener(new PasteListener());
        getJDA().addEventListener(new PasteHandler());
        getJDA().addEventListener(new ButtonHandler());
        getJDA().addEventListener(new AutoWiki());
        getJDA().addEventListener(new Prefix());
        getJDA().addEventListener(new UserWatcher());
        getJDA().addEventListener(new ReactionDirector());
        // Commands
        getJDA().addEventListener(new Eco()); // Money Subcommand
        getJDA().addEventListener(new Links());
        getJDA().addEventListener(new Stats());
        getJDA().addEventListener(new Ping());
        getJDA().addEventListener(new RRoles());
        getJDA().addEventListener(new Poll());
        getJDA().addEventListener(new Log());
//        getJDA().addEventListener(new Paste()); REPLACED WITH PASTE HANDLER
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
        LOGGER.info("{} IS WATCHING THE UNIVERSE", e.getJDA().getSelfUser().getAsTag());
        System.out.println("[ BOT HAS STARTED! ]");

        J.a(() -> {
            J.sleep(1000);
            System.out.println("[INFO]: Cleaning unused roles from guild");
            List<String> ff = new ArrayList<>();
            for (Role r : getJDA().getGuilds().get(0).getRoles()) {
                Role role = getJDA().getGuilds().get(0).getRoleById(getJDA().getGuilds().get(0).getRolesByName(r.getName(), false).get(0).getIdLong());
                List<Member> members = getJDA().getGuilds().get(0).getMembersWithRoles(role);
                if (members.size() == 0 && r.getName().contains(Toolkit.get().LevelName)) {
                    getJDA().getGuilds().get(0).getRolesByName(r.getName(), true).get(0).delete().queue();
                    ff.add(getJDA().getGuilds().get(0).getRolesByName(r.getName(), true).get(0).getName());
                }
            }
            if (ff.size() > 0) {
                System.out.println("[INFO]: Cleaned roles: " + ff);
            } else {
                System.out.println("[INFO]: No roles to clean!");
            }
        });
    }

    public static void shutdown() {
        getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
        getJDA().shutdown();
        System.exit(1);
    }

    private static void log(String tag, Object t) {
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

