/*
 * tvmaze-java-client - A client to access the TVMaze API
 * Copyright © 2024 Andy Miles (andy.miles@amilesend.com)
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
package com.amilesend.tvmaze.client;

import com.amilesend.tvmaze.client.api.EpisodesApi;
import com.amilesend.tvmaze.client.api.PeopleApi;
import com.amilesend.tvmaze.client.api.ScheduleApi;
import com.amilesend.tvmaze.client.api.SearchApi;
import com.amilesend.tvmaze.client.api.ShowsApi;
import com.amilesend.tvmaze.client.api.UpdatesApi;
import com.amilesend.tvmaze.client.connection.Connection;
import lombok.RequiredArgsConstructor;

/**
 * A helper class to vend API classes that are associated with a {@link Connection} to the TVMaze service.
 *
 * @see Connection
 */
@RequiredArgsConstructor
public class TvMaze {
    private final Connection connection;

    /** Creates a new {@code TvMaze} object that is configured with the default settings. */
    public TvMaze() {
        connection = Connection.newDefaultInstance();
    }

    /**
     * Gets a new {@link SearchApi} object used to interact with the Search API. See
     * <a href="https://www.tvmaze.com/api#search">https://www.tvmaze.com/api#search</a> for more information.
     *
     * @return the search API
     * @see SearchApi
     */
    public SearchApi getSearchApi() {
        return new SearchApi(connection);
    }

    /**
     * Gets a new {@link ScheduleApi} object used to interact with the Schedule API. See
     * a href="https://www.tvmaze.com/api#schedule">https://www.tvmaze.com/api#schedule</a> for more information.
     *
     * @return the schedule API
     * @see ScheduleApi
     */
    public ScheduleApi getScheduleApi() {
        return new ScheduleApi(connection);
    }

    /**
     * Gets a new {@link ScheduleApi} object used to interact with the Shows API. See
     * a href="https://www.tvmaze.com/api#shows">https://www.tvmaze.com/api#shows</a> for more information.
     *
     * @return the schedule API
     * @see ShowsApi
     */
    public ShowsApi getShowsApi() {
        return new ShowsApi(connection);
    }

    /**
     * Gets a new {@link EpisodesApi} object used to interact with the Episodes API. See
     * a href="https://www.tvmaze.com/api#episodes">https://www.tvmaze.com/api#episodes</a> for more information.
     *
     * @return the episodes API
     * @see EpisodesApi
     */
    public EpisodesApi getEpisodesApi() {
        return new EpisodesApi(connection);
    }

    /**
     * Gets a new {@link PeopleApi} object used to interact with the People API. See
     * a href="https://www.tvmaze.com/api#people">https://www.tvmaze.com/api#people</a> for more information.
     *
     * @return the people API
     * @see PeopleApi
     */
    public PeopleApi getPeopleApi() {
        return new PeopleApi(connection);
    }

    /**
     * Gets a new {@link UpdatesApi} object used to interact with the Updates API. See
     * a href="https://www.tvmaze.com/api#updates">https://www.tvmaze.com/api#updates</a> for more information.
     *
     * @return the updates API
     * @see UpdatesApi
     */
    public UpdatesApi getUpdatesApi() {
        return new UpdatesApi(connection);
    }
}
