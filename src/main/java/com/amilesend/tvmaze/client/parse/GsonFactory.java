/*
 * tvmaze-java-client - A client to access the TVMaze API
 * Copyright © 2024-2025 Andy Miles (andy.miles@amilesend.com)
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.amilesend.tvmaze.client.parse;

import com.amilesend.tvmaze.client.connection.Connection;
import com.amilesend.tvmaze.client.parse.adapters.LocalDateTimeTypeAdapter;
import com.amilesend.tvmaze.client.parse.adapters.LocalDateTypeAdapter;
import com.amilesend.tvmaze.client.parse.adapters.LocalTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Factory that vends new pre-configured {@link Gson} instances. */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class GsonFactory {
    private static final GsonFactory INSTANCE = new GsonFactory();

    /**
     * Gets the singleton {@code GsonFactory} instance.
     *
     * @return the factory instance
     */
    public static GsonFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Gets a new {@link Gson} instance that is configured for use by {@link Connection}.
     *
     * @return the pre-configured Gson instance
     */
    public Gson newInstanceForConnection() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();
    }

    /**
     * Gets a new {@link Gson} instance that is configured for use by {@link Connection} that provides pretty-printed
     * formatted JSON (i.e., useful for testing and/or debugging).
     *
     * @return the pre-configured Gson instance
     */
    public Gson newInstanceForPrettyPrinting() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();
    }
}
