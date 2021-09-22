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

package com.volmit.abyssalith.listeners.handlers;

import com.volmit.abyssalith.Main;
import com.volmit.abyssalith.data.User;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class WarningHandler {

    public static void warn(Member m, Guild guild) {
        User u = Main.getLoader().getUser(m.getIdLong());
        int warns = u.warnings().size();
        if (warns < 3) {
            m.getUser().openPrivateChannel().complete().sendMessage("**You have been warned.** \nPlease cease whatever it is that you are doing.").queue();
            System.println("[INFO]-> Sent message to " + m);
        } else if (warns == 3) {
            m.getUser().openPrivateChannel().complete().sendMessage("**You have been Kicked.** \nYou can rejoin, just think about what you have done").queue();
            System.println("[INFO]-> Sent message to " + m);
            warnFinal(m, guild);
        } else {
//            BanishHandler.bok(m); // Apply Banished
            m.getUser().openPrivateChannel().complete().sendMessage("**You are Banned from Volmit Software's Discord.** \nShould have listened. **Banned**").queue();
            System.println("[INFO]-> Sent message to " + m);
            goodBye(m, guild);
        }
    }

    public static void warnFinal(Member m, Guild g) {
        g.kick(m).complete();
        System.println("[INFO]-> Kicked member: " + m);
    }

    public static void goodBye(Member m, Guild g) {
        g.ban(m, 7, "5 warnings, not going to be missed").complete();
        System.println("[INFO]-> Perm Ban set to: " + m);

    }
}
