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

package com.volmit.abyssalith.bot;

import com.volmit.abyssalith.toolbox.Kit;
import lombok.SneakyThrows;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GarbageDigitalOceanHealthCheckWebServerEchoServer {
    private Server server;

    @SneakyThrows
    public GarbageDigitalOceanHealthCheckWebServerEchoServer() throws Exception {
        server = new Server(Kit.get().DummyPort);
        ServletContextHandler s = new ServletContextHandler(server, "/");
        s.addServlet(Ser.class, "/helth");

        server.start();
        w("HELTH SERVER STARTED");
    }

    public static class Ser extends HttpServlet
    {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.getWriter().println("Hlathy");
            resp.getWriter().flush();
            resp.setStatus(200);
            resp.flushBuffer();
        }
    }
}
