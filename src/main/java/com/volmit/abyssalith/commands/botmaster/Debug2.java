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
package com.volmit.abyssalith.commands.botmaster;

import com.volmit.abyssalith.util.VolmitCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;


public class Debug2 extends VolmitCommand {

    // Constructor
    public Debug2() {
        super(
                "debug",
                new String[]{"db"},
                new String[]{},
                "This is a Debug command for Time",
                false,
                null
        );
    }

    // Handle
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent e) {

        String hostname = "time.nist.gov"; // you can ping here for the current date and time from the server
        // https://www.nist.gov/pml/time-and-frequency-division/about-timegov#:~:text=This%20website%20is%20provided%20by,Standards%20and%20Technology%20(NIST).&text=UTC(NIST)%20serves%20as%20a,day%20for%20the%20United%20States.
        // http://www.nlsa.com/help/alternative_time_servers.html
        int port = 13; // What port am I opening to do the query
        int buffer; // character number

        try (Socket socket = new Socket(hostname, port)) {
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            StringBuilder data = new StringBuilder();

            while ((buffer = reader.read()) != -1) {
                data.append((char) buffer);
            }
            e.getChannel().sendMessage("The current time on `time.nist.gov` is:\n"+ data).queue();

        } catch (Exception ignored) {} // its not like im using it for anything
    }
}
