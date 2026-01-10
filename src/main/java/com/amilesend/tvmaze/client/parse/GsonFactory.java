/*
 * tvmaze-java-client - A client to access the TVMaze API
 * Copyright © 2024-2026 Andy Miles (andy.miles@amilesend.com)
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

import com.amilesend.client.connection.Connection;
import com.amilesend.client.parse.GsonFactoryBase;
import com.amilesend.tvmaze.client.parse.adapters.LocalDateTimeTypeAdapter;
import com.amilesend.tvmaze.client.parse.adapters.LocalDateTypeAdapter;
import com.amilesend.tvmaze.client.parse.adapters.LocalTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Factory that vends new pre-configured {@link Gson} instances.
 *
 * @see GsonFactoryBase
 * @see Connection
 */
@NoArgsConstructor
public class GsonFactory extends GsonFactoryBase<Connection> {
    @Override
    protected GsonBuilder configure(final GsonBuilder gsonBuilder, final Connection connection) {
        return gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter());
    }
}
