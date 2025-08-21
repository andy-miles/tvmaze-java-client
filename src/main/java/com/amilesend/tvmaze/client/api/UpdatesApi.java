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
package com.amilesend.tvmaze.client.api;

import com.amilesend.client.connection.Connection;
import com.amilesend.client.parse.parser.MapParser;
import com.amilesend.client.util.Validate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;

import java.util.Map;
import java.util.Objects;


/**
 * TVMaze API to retrieve database update information
 * <br/>
 * For more information, please refer to <a href="https://www.tvmaze.com/api#updates">
 * https://www.tvmaze.com/api#updates</a>
 */
public class UpdatesApi extends ApiBase {
    private static final String SHOW_UPDATES_API_PATH = "/updates/shows";
    private static final String PERSON_UPDATES_API_PATH = "/updates/people";

    /**
     * Creates a new {@code UpdateApi} object.
     *
     * @param connection the connection
     */
    public UpdatesApi(final Connection connection) {
        super(connection);
    }

    ///////////////////
    // getShowUpdates
    ///////////////////

    /**
     * Retrieves a map of shows in the TVMaze database and the corresponding last updated timestamp.
     *
     * @param since specifies the time range limit to apply to the query. Note: can be {@code null} for all tv shows
     * @return a map of updates with the key as the show identifier and the value as the last updated timestamp
     */
    public Map<Integer, Long> getShowUpdates(final Since since) {
        final HttpUrl url = validateAndFormatUpdatesUrl(SHOW_UPDATES_API_PATH, since);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new MapParser<>(Integer.class, Long.class));
    }

    /////////////////////
    // getPersonUpdates
    /////////////////////

    /**
     * Retrieves a map of persons in the TVMaze database and the corresponding last updated timestamp.
     *
     * @param since specifies the time range limit to apply to the query. Note: can be {@code null} for all people
     * @return a map of updates with the key as the person identifier and the value as the last updated timestamp
     */
    public Map<Integer, Long> getPersonUpdates(final Since since) {
        final HttpUrl url = validateAndFormatUpdatesUrl(PERSON_UPDATES_API_PATH, since);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new MapParser<>(Integer.class, Long.class));
    }

    private HttpUrl validateAndFormatUpdatesUrl(final String apiPath, final Since since) {
        Validate.notBlank(apiPath, "apiPath must not be blank");

        final HttpUrl.Builder urlBuilder = HttpUrl.parse(connection.getBaseUrl() + apiPath).newBuilder();
        if (Objects.nonNull(since)) {
            urlBuilder.addQueryParameter("since", since.getQueryParameter());
        }

        return urlBuilder.build();
    }

    /** The time range limiting specifier. */
    @RequiredArgsConstructor
    public enum Since {
        DAY("day"),
        WEEK("week"),
        MONTH("month");

        @Getter
        private final String queryParameter;
    }
}
