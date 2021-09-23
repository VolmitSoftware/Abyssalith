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

package com.volmit.abyssalith;

import art.arcane.amulet.logging.LogListener;
import art.arcane.quill.execution.J;
import art.arcane.quill.execution.Looper;
import com.volmit.abyssalith.commands.Shutdown;
import com.volmit.abyssalith.commands.*;
import com.volmit.abyssalith.io.DataLoader;
import com.volmit.abyssalith.io.storage.FileSystemStorageAccess;
import com.volmit.abyssalith.io.storage.RedisStorageAccess;
import com.volmit.abyssalith.listeners.*;
import com.volmit.abyssalith.listeners.handlers.ButtonHandler;
import com.volmit.abyssalith.listeners.handlers.MenuHandler;
import com.volmit.abyssalith.listeners.handlers.PasteHandler;
import com.volmit.abyssalith.listeners.handlers.ReactionHandler;
import com.volmit.abyssalith.toolbox.Kit;
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
    private static DataLoader loader;

    // BOT BUILDER BOYS
    public static JDA getJDA() {
        return provider.get().getJDA();
    }

    public static void main(String[] args) throws LoginException {
        new Thread(() -> {
            try {
                new GarbageDigitalOceanHealthCheckWebServerEchoServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        LogListener.listener.set(new LogListener() {

            @Override
            public void i(String tag, Object f) {
                info(tag + ": " + f);
            }

            @Override
            public void f(String tag, Object f) {
                error(tag + ": " + f);
            }

            @Override
            public void w(String tag, Object f) {
                warn(tag + ": " + f);
            }

            @Override
            public void d(String tag, Object f) {
                debug(tag + ": " + f);
            }
        });
        envInject();


        if(Kit.get().UseRedis)
        {
            Kit k = Kit.get();
            loader = new DataLoader(new RedisStorageAccess(k.RedisAddress, k.RedisPort, k.RedisPassword));
        }

        else
        {
            loader = new DataLoader(new FileSystemStorageAccess(new File("botdata")));
        }


        org.slf4j.simple.SimpleServiceProvider.class.getSimpleName();
        // Status
        System.println("Initializing");

        Kit.get().botID = getJDA().getSelfUser().getIdLong();
        Kit.get().botUser = getJDA().getUserById(Kit.get().botID);
        Kit.get().botName = Objects.requireNonNull(Kit.get().botUser).getName();

        // Log incoming messages
        getJDA().addEventListener(new Main());
        // Listeners
        getJDA().addEventListener(new Kit());
        //Hanlers
        getJDA().addEventListener(new MenuHandler());
        getJDA().addEventListener(new PasteHandler());
        getJDA().addEventListener(new ButtonHandler());
        //Listeners
        getJDA().addEventListener(new BotListener()); // Watches the Bot instance's for stuff
        getJDA().addEventListener(new LanguageListener());
        getJDA().addEventListener(new PasteListener());
        getJDA().addEventListener(new PersistentRoleListener()); // Persistent Roles
        getJDA().addEventListener(new PhishingListener()); // Persistent Roles
        getJDA().addEventListener(new ReactionHandler());
        getJDA().addEventListener(new UserListener()); // Watches the User's instances for stuff

        // Commands
        getJDA().addEventListener(new Eco()); // Money Subcommand
        getJDA().addEventListener(new Links());
        getJDA().addEventListener(new Warn());
        getJDA().addEventListener(new GStats());
        getJDA().addEventListener(new MStats());
        getJDA().addEventListener(new Passive());
        getJDA().addEventListener(new RRoles());
        getJDA().addEventListener(new Poll());
        getJDA().addEventListener(new Log());
        getJDA().addEventListener(new Shutdown());
        getJDA().addEventListener(new Commands(getJDA())); // This one MUST be last

        new Looper() {
            @Override
            protected long loop() {
                Kit.tick();
                return 1000;   
            }
        }.start();

        Runtime.getRuntime().addShutdownHook(new Thread(loader::close));
    }

    private static void envInject() {
        Kit.get().envInject();
    }

    @Override
    public void onReady(@NonNull ReadyEvent e) {
        LOGGER.debug("{} IS WATCHING THE UNIVERSE", e.getJDA().getSelfUser().getAsTag());
        i("BOT HAS STARTED!");

        J.a(() -> {
            J.sleep(1000);
            i("Cleaning unused roles from guild");
            List<String> ff = new ArrayList<>();
            for (Role r : getJDA().getGuilds().get(0).getRoles()) {
                Role role = getJDA().getGuilds().get(0).getRoleById(getJDA().getGuilds().get(0).getRolesByName(r.getName(), false).get(0).getIdLong());
                List<Member> members = getJDA().getGuilds().get(0).getMembersWithRoles(role);
                if (members.size() == 0 && r.getName().contains(Kit.get().LevelName)) {
                    getJDA().getGuilds().get(0).getRolesByName(r.getName(), true).get(0).delete().queue();
                    ff.add(getJDA().getGuilds().get(0).getRolesByName(r.getName(), true).get(0).getName());
                }
            }
            if (ff.size() > 0) {
                i("Cleaned roles: ");
                System.println( ff );
            } else {
                i("No roles to clean!");
            }
        });
    }

    public static void shutdown() {
        System.println("Terminating the bot instance");
        getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
        getJDA().shutdown();
        System.exit(1);
    }

    private static void log(String tag, Object t) {
        System.println("[" + tag + "]-> " + t);
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

