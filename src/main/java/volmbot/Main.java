package volmbot;

import art.arcane.quill.execution.Looper;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import volmbot.commands.*;
import volmbot.data.User;
import volmbot.io.DataLoader;
import volmbot.io.storage.FileSystemStorageAccess;
import volmbot.listeners.*;
import volmbot.toolbox.Toolkit;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.net.http.WebSocket;
import java.util.Objects;


public class Main extends ListenerAdapter {
    public static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.Listener.class);
    public static final IBotProvider provider = new BotProvider();

   @Getter
    private static final DataLoader loader = createLoader();

    private static DataLoader createLoader() {
        return new DataLoader(new FileSystemStorageAccess(new File("newdata")));
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

        /// Listener Registrar
        // Log incoming messages
        getJDA().addEventListener(new Main());

        // Listeners
        getJDA().addEventListener(new Toolkit());
        getJDA().addEventListener(new OwOListener());
        getJDA().addEventListener(new AutoWiki());
        getJDA().addEventListener(new Prefix());
        getJDA().addEventListener(new ReactionListener()); //BROKEN
        // Money Commands
        getJDA().addEventListener(new Eco());

        // Commands
        getJDA().addEventListener(new ReactionRole());
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

        User u = loader.getUser(1234);
        u.experience(69).points(96723);
        loader.cleanup(-1);

        Runtime.getRuntime().addShutdownHook(new Thread(loader::close));
    }



    @Override
    public void onReady(@NonNull ReadyEvent e) {
        LOGGER.info("{} IS WATCHING THE UNIVERSE", e.getJDA().getSelfUser().getAsTag());
        System.out.println("[ BOT HAS STARTED! ]");
    }

    public static void shutdown() {
        getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
        getJDA().shutdown();
        System.exit(1);
    }

    public static void warn(String message) {
        LOGGER.warn(" \\/ {}", message);
    }

    public static void info(String message) {
        LOGGER.info(" \\/ {}", message);
    }

    public static void error(String message) {
        LOGGER.error(" \\/ {}", message);
    }

    public static void debug(String message) {
        LOGGER.debug(" \\/ {}", message);
    }
}

