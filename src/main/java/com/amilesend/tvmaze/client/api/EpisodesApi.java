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
package com.amilesend.tvmaze.client.api;

import com.amilesend.tvmaze.client.connection.Connection;
import com.amilesend.tvmaze.client.model.Episode;
import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.CrewMember;
import com.amilesend.tvmaze.client.parse.parser.BasicParser;
import com.amilesend.tvmaze.client.parse.parser.ListParser;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * TVMaze API to retrieve episode information.
 * <br/>
 * For more information, please refer to <a href="https://www.tvmaze.com/api#episodes">
 * https://www.tvmaze.com/api#episodes</a>
 */
public class EpisodesApi extends ApiBase {
    private static final String EPISODES_API_PATH = "/episodes/";
    private static final String GUEST_CAST_SUB_API_PATH = "/guestcast";
    private static final String GUEST_CREW_SUB_API_PATH = "/guestcrew";

    /**
     * Creates a new {@code EpisodesApi} object.
     *
     * @param connection the connection
     */
    public EpisodesApi(final Connection connection) {
        super(connection);
    }

    ///////////////
    // getEpisode
    ///////////////

    /**
     * Retrieves the episode for the given identifier.
     *
     * @param episodeId the episode identifier
     * @param isShowIncluded if {@code true}, includes the embedded show information in the response; else,
     *                       {@code false}
     * @return the episode
     * @see Episode
     * @see com.amilesend.tvmaze.client.model.Show
     */
    public Episode getEpisode(final int episodeId, final boolean isShowIncluded) {
        final HttpUrl url = validateAndFormatEpisodeUrl(episodeId, isShowIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new BasicParser<>(Episode.class));
    }

    private HttpUrl validateAndFormatEpisodeUrl(final int episodeId, final boolean isShowIncluded) {
        return isShowIncluded
                ? validateAndFormatUrl(EPISODES_API_PATH, episodeId, StringUtils.EMPTY, Episode.EmbeddedType.SHOW)
                : validateAndFormatUrl(EPISODES_API_PATH, episodeId, StringUtils.EMPTY);
    }

    /////////////////
    // getGuestCast
    /////////////////

    /**
     * Retrieves the list of guest cast members for an episode.
     *
     * @param episodeId the episode identifier
     * @return the list of guest cast members
     * @see CastMember
     */
    public List<CastMember> getGuestCast(final int episodeId) {
        final HttpUrl url = validateAndFormatUrl(EPISODES_API_PATH, episodeId, GUEST_CAST_SUB_API_PATH);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new ListParser<>(CastMember.class));
    }

    /////////////////
    // getGuestCrew
    /////////////////

    /**
     * Retrieves the list of guest crew members for an episode.
     *
     * @param episodeId the episode identifier
     * @return the list of guest crew members
     * @see CrewMember
     */
    public List<CrewMember> getGuestCrew(final int episodeId) {
        final HttpUrl url = validateAndFormatUrl(EPISODES_API_PATH, episodeId, GUEST_CREW_SUB_API_PATH);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new ListParser<>(CrewMember.class));
    }
}
