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
package com.amilesend.tvmaze.client.parse.adapters;

import com.amilesend.client.util.StringUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/** GSON adapter to format and series {@link java.time.LocalDateTime} objects. */
public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Override
    public JsonElement serialize(
            final LocalDateTime dateTime,
            final Type typeOfSrc,
            final JsonSerializationContext context) {
        return Optional.ofNullable(dateTime)
                .map(dt -> new JsonPrimitive(dt.format(DateTimeFormatter.ISO_DATE_TIME)))
                .orElse(null);
    }

    @Override
    public LocalDateTime deserialize(
            final JsonElement jsonElement,
            final Type typeOfT,
            final JsonDeserializationContext context) throws JsonParseException {
        final String dateTimeAsString = jsonElement.getAsString();
        if (StringUtils.isBlank(dateTimeAsString)) {
            return null;
        }

        return LocalDateTime.parse(dateTimeAsString, DateTimeFormatter.ISO_DATE_TIME);
    }
}
