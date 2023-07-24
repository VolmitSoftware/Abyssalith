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
import com.volmit.abyssalith.commands.moderation.ModHub;
import com.volmit.abyssalith.commands.listeners.*;
import com.volmit.abyssalith.toolbox.Kit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SkipCommand
public class Registrar extends ListenerAdapter {

    /**
     * Command package path. Recursively searched for commands not annotated by {@link SkipCommand}
     */
    private static final String commandPackagePath = Registrar.class.getPackage().getName();

    public static void All(JDA jda) {
        // Main bits, Regardless of platform
        jda.addEventListener(new Abyss()); // [ DONT TOUCH THESE  LISTENERS ]
        jda.addEventListener(new Kit());  // [ DONT TOUCH THESE  LISTENERS ]
        jda.addEventListener(new Shutdown());  // [ DONT TOUCH THESE  LISTENERS ]

        //Commands - General
        jda.addEventListener(new Chunky());
        jda.addEventListener(new Install());
        jda.addEventListener(new Links());
        jda.addEventListener(new Log());
        jda.addEventListener(new Passive());
        jda.addEventListener(new Paste());
        jda.addEventListener(new ServerInfo());
        jda.addEventListener(new UserInfo());



        jda.addEventListener(new com.volmit.abyssalith.commands.general.Commands(jda)); // This one MUST be last
    }

    /**
     * Register all commands
     * @param jda the {@link JDA} to register to
     */
    private static void registerAllCommands(String packagePath, JDA jda) throws NullPointerException, IOException {

        // Get stream of class data
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packagePath.replaceAll("[.]", "/"));

        // If stream not accessible (null)
        if (stream == null) {
            throw new NullPointerException("Command loading, package not found: " + packagePath);
        }

        List<String> loadedCommands = new ArrayList<>();
        new BufferedReader(new InputStreamReader(stream))
                .lines()
                .filter(line -> {
                    if (line.endsWith(".class")) {
                        return true;
                    }

                    if (!line.contains(".")) {
                        try {
                            registerAllCommands(packagePath + "." + line, jda);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                })
                .map(line -> getCommandClass(line, packagePath))
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
                .filter(Objects::nonNull)
                .forEach(c -> {
                    jda.addEventListener(c);
                    loadedCommands.add(c.getClass().getSimpleName());
                });
        stream.close();
        Abyss.debug("Loaded " + (loadedCommands.isEmpty() ? "NONE" : String.join(", ", loadedCommands)) + " from package " + packagePath);
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
            if (c.isAssignableFrom(ListenerAdapter.class)) {
                Abyss.debug("Unable to load class: " + c.getName() + " because it does not extend ListenerAdapter");
            }
            return c;
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}
