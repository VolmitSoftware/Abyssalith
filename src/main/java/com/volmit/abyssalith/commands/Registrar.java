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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;


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
        Abyss.info("Command packages loaded: " + Arrays.toString(commandPackagePaths));
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

            default: // What it all has to offer
                //Listeners
                jda.addEventListener(new ButtonListener());
                jda.addEventListener(new PasteListener());
                jda.addEventListener(new GuildListener()); // Persistent Roles
                jda.addEventListener(new SelectionMenuListener());
                jda.addEventListener(new MessageReactionListener()); // Watches the User's instances for stuff
                jda.addEventListener(new MessageListener()); // Watches the User's messages for stuff

                // Commands
                jda.addEventListener(new EcoHub()); // Money Subcommand
                jda.addEventListener(new ModHub()); // Money Subcommand
                jda.addEventListener(new Links());
                jda.addEventListener(new Install());
                jda.addEventListener(new Paste());
                jda.addEventListener(new ServerInfo());
                jda.addEventListener(new UserInfo());
                jda.addEventListener(new Passive());
                jda.addEventListener(new RoleMenu());
                jda.addEventListener(new Log());
                break;
        }

        jda.addEventListener(new com.volmit.abyssalith.commands.general.Commands(jda)); // This one MUST be last
    }

    private void registerAllCommands() {

    }

    /**
     * Get all command instances in a list of packages.
     * @param packages the list of packages
     * @return the set of command instances
     */
    private Set<ListenerAdapter> getAllCommands(String[] packages) {
        Set<ListenerAdapter> listenerAdapters = new HashSet<>();
        for (String subPackage : packages) {
            getAllCommandClasses(subPackage).forEach(c -> {
                try {
                    listenerAdapters.add((ListenerAdapter) c.getConstructors()[0].newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
        return listenerAdapters;
    }

    /**
     * Get all classes from a package.
     * @param packageName the package name
     * @return a set of ListenerAdapter classes (empty if none found)
     */
    private Set<Class<? extends ListenerAdapter>> getAllCommandClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream == null) {
            return new HashSet<>();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getCommandClass(line, packageName))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Get a command class from a class name and package.
     * @param className name of the class
     * @param packageName path to the package
     * @return the class or null if not a {@link ListenerAdapter}
     */
    private Class<? extends ListenerAdapter> getCommandClass(String className, String packageName) {
        try {
            Class<?> c = Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
            if (!c.isAssignableFrom(ListenerAdapter.class)) {
                return (Class<? extends ListenerAdapter>) c;
            } else {
                Abyss.debug("Unable to load class: " + c.getName() + " because it does not extend ListenerAdapter");
            }
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}
