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
package com.volmit.abyssalith.bot.instance;

import com.volmit.abyssalith.toolbox.Kit;

import javax.security.auth.login.LoginException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;


public class BotProvider implements IBotProvider {

    private final ExecutorService executorService;
    private final AtomicReference<String> token;
    private final AtomicReference<CompletableFuture<BotInstance>> future;

    public BotProvider() {
        token = new AtomicReference<>(realToken());
        future = new AtomicReference<>();
        executorService = Executors.newWorkStealingPool(8);
    }

    private String realToken() {
        return Kit.get().botToken;
    }

    @Override
    public CompletableFuture<BotInstance> getFuture() {
        String rt = realToken();
        if (!token.get().equals(rt)) {
            future.set(null);
        }

        if (future.get() == null) {
            token.set(rt);
            future.set(CompletableFuture.supplyAsync(() -> {
                try {
                    return new BotInstance(token.get());
                } catch (LoginException e) {
                    e.printStackTrace();
                    return null;
                }
            }, executorService));
        }

        return future.get();
    }
}
