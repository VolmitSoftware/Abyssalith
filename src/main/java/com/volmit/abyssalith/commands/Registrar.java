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
package com.volmit.abyssalith.commands;

import com.volmit.abyssalith.Abyss;
import com.volmit.abyssalith.commands.general.Shutdown;
import com.volmit.abyssalith.commands.general.*;
import com.volmit.abyssalith.commands.moderation.EcoHub;
import com.volmit.abyssalith.commands.moderation.ModHub;
import com.volmit.abyssalith.commands.moderation.reactionroles.RoleMenu;
import com.volmit.abyssalith.commands.listeners.*;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.internal.annotations.EverythingIsNonNull;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

public class Registrar extends ListenerAdapter {

    /**
     * Command package paths. Add more here if you make a new subdirectory!
     */
    private static final String[] commandPackagePaths = new String[]{
            Registrar.class.getPackage().getName() + ".general",
            Registrar.class.getPackage().getName() + ".listeners",
            Registrar.class.getPackage().getName() + ".moderation",
            Registrar.class.getPackage().getName() + ".moderation.banish",
            Registrar.class.getPackage().getName() + ".moderation.eco",
            Registrar.class.getPackage().getName() + ".moderation.reactionroles",
            Registrar.class.getPackage().getName() + ".moderation.warning",
    };

    static {
        Abyss.info("Command packages registered: " + Arrays.toString(commandPackagePaths));
    }

    public static void All(JDA jda) {
        // Main bits, Regardless of platform
        jda.addEventListener(new Abyss()); // [ DONT TOUCH THESE  LISTENERS ]
        jda.addEventListener(new Kit());  // [ DONT TOUCH THESE  LISTENERS ]
        jda.addEventListener(new Shutdown());  // [ DONT TOUCH THESE  LISTENERS ]

        String modules = Kit.get().usageModules;

        switch (modules) {
            case "MPM":
//                jda.addEventListener(new ServerInfo()); HELTH STUFF

                break;

            case "DEV":
//                jda.addEventListener(new ServerInfo()); DEVELOPMENT KEY

                break;

            case "VOL":
//                jda.addEventListener(new ServerInfo()); VOLMIT SERVER STANDARDS
                jda.addEventListener(new ButtonListener());
                jda.addEventListener(new PasteListener());
                jda.addEventListener(new Install());
                jda.addEventListener(new Log());
                jda.addEventListener(new UserInfo());
                jda.addEventListener(new GuildListener()); // Persistent Roles
                jda.addEventListener(new MessageListener()); // Watches the User's messages for stuff
                jda.addEventListener(new MessageReactionListener()); // Watches the User's instances for stuff
                jda.addEventListener(new Paste());
                jda.addEventListener(new Passive());
                jda.addEventListener(new ModHub()); // Mod Subcommand

                break;

            default:
                Abyss.debug("Registering commands...");
                registerAllCommands(jda);
                break;
        }

        jda.addEventListener(new com.volmit.abyssalith.commands.general.Commands(jda)); // This one MUST be last
    }

    /**
     * Get and register all command classes available.
     * @param jda the JDA to register to.
     */
    private static void registerAllCommands(JDA jda) {
        for (String commandPackagePath : commandPackagePaths) {
            List<String> loadedCommands = new ArrayList<>();
            getAllCommands(commandPackagePath).forEach(c -> {
                jda.addEventListener(c);
                loadedCommands.add(c.getClass().getSimpleName());
            });
            Abyss.debug("Loaded " + String.join(", ", loadedCommands) + " from package " + commandPackagePath);
        }
    }

    /**
     * Get all classes from a package.
     * @param packageName the package name
     * @return a set of ListenerAdapter classes (empty if none found)
     */
    private static @NotNull Stream<ListenerAdapter> getAllCommands(@NotNull String packageName) throws NullPointerException {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));

        if (stream == null) {
            throw new NullPointerException("Command loading, package not found: " + packageName);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getCommandClass(line, packageName))
                .filter(Objects::nonNull)
                .filter(c -> !c.isAnnotationPresent(SkipCommand.class))
                .map(cmdClass -> {
                    try {
                        return (ListenerAdapter) cmdClass.getConstructors()[0].newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                        e.printStackTrace();
                        Abyss.debug("Failed to load command " + cmdClass.getName() + " with empty constructor!");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        Abyss.debug("Failed to load command " + cmdClass.getName() + " due to no constructor being present!");
                    }
                    return null;
                })
                .filter(Objects::nonNull);
    }

    /**
     * Get a command class from a class name and package.
     * @param className name of the class
     * @param packageName path to the package
     * @return the class or null if not a {@link ListenerAdapter}
     */
    private static Class<?> getCommandClass(String className, String packageName) {
        try {
            Class<?> c = Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
            if (!c.isAssignableFrom(ListenerAdapter.class)) {
                return c;
            } else {
                Abyss.debug("Unable to load class: " + c.getName() + " because it does not extend ListenerAdapter");
            }
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}
