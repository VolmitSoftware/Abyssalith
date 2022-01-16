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

import art.arcane.quill.execution.J;
import art.arcane.quill.execution.Looper;
import com.volmit.abyssalith.bot.instance.BotProvider;
import com.volmit.abyssalith.bot.instance.IBotProvider;
import com.volmit.abyssalith.bot.startup.Registrar;
import com.volmit.abyssalith.bot.startup.RoleCleanup;
import com.volmit.abyssalith.io.DataLoader;
import com.volmit.abyssalith.io.storage.FileSystemStorageAccess;
import com.volmit.abyssalith.io.storage.RedisStorageAccess;
import com.volmit.abyssalith.toolbox.Kit;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;


public class Abyss extends ListenerAdapter {

    public static void main(String[] args) {

        //This sets the threading priorities to work on Droplet
        final int cores = Runtime.getRuntime().availableProcessors();
        if (cores <= 1) {
            System.out.println("Available Cores \"" + cores + "\", Attempting to set Parallelism Flag");
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1");
            System.out.println("Parallelism Set!");
        }

        envInject();

        if (Kit.get().UseRedis) {
            Kit k = Kit.get();
            loader = new DataLoader(new RedisStorageAccess(k.RedisAddress, k.RedisPort, k.RedisPassword));
        } else {
            loader = new DataLoader(new FileSystemStorageAccess(new File("Data/BotData")));
        }

        org.slf4j.simple.SimpleServiceProvider.class.getSimpleName();
        System.out.println("Initializing");
        Kit.get().botID = getJDA().getSelfUser().getIdLong();
        Kit.get().botUser = getJDA().getUserById(Kit.get().botID);
        Kit.get().botName = Objects.requireNonNull(Kit.get().botUser).getName();
        Registrar.All(getJDA()); // ALL COMMANDS ARE HERE

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

    public static final IBotProvider provider = new BotProvider();

    @Getter
    private static DataLoader loader;

    public static JDA getJDA() {


        return provider.get().getJDA();
    }

    @Override
    public void onReady(@NonNull ReadyEvent e) {
        System.out.println(e.getJDA().getSelfUser().getAsTag()+" IS WATCHING THE UNIVERSE");
        J.a(() -> {
            J.sleep(1500);
            RoleCleanup.Cleaner(getJDA());
        });
        info("BOT HAS STARTED!");
        getJDA().getUserById(Kit.get().BotOwnerID).openPrivateChannel().complete().sendMessage("AHH").queue();
    }

    public static void shutdown() {
        System.out.println("Terminating the bot instance");
        getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
        getJDA().shutdown();
        System.exit(1);
    }

    private static void log(String tag, Object t) {
        System.out.println("[" + tag + "]-> " + t);
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

