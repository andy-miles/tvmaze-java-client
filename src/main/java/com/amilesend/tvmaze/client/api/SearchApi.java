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
import com.amilesend.client.parse.parser.BasicParser;
import com.amilesend.client.parse.parser.ListParser;
import com.amilesend.client.util.Validate;
import com.amilesend.tvmaze.client.model.Person;
import com.amilesend.tvmaze.client.model.Show;
import com.amilesend.tvmaze.client.model.type.PersonResult;
import com.amilesend.tvmaze.client.model.type.ShowResult;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;

import java.util.List;


/**
 * TVMaze API to search for show information.
 * <br/>
 * For more information, please refer to: <a href="https://www.tvmaze.com/api#search">https://www.tvmaze.com/api#search
 * </a>
 */
@Slf4j
public class SearchApi extends ApiBase {
    private static final String SEARCH_SHOWS_API_PATH = "/search/shows";
    private static final String SINGLE_SEARCH_SHOWS_API_PATH = "/singlesearch/shows";
    private static final String LOOKUP_SHOWS_API_PATH = "/lookup/shows";
    private static final String SEARCH_PEOPLE_API_PATH = "/search/people";
    private static final int MAX_QUERY_LENGTH = 256;

    /**
     * Creates a new {@code SearchApi} object.
     *
     * @param connection the connection
     */
    public SearchApi(final Connection connection) {
        super(connection);
    }

    ////////////////
    // searchShows
    ////////////////

    /**
     * Search for TV shows for the given query.
     *
     * @param query the search query
     * @return the list of shows for the associated query
     * @see ShowResult
     */
    public List<ShowResult> searchShows(final String query) {
        final HttpUrl url = validateAndFormatSearchUrl(SEARCH_SHOWS_API_PATH, query);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new ListParser<>(ShowResult.class));
    }

    /////////////////////
    // singleSearchShow
    /////////////////////

    /**
     * Search for and return a single show for the given query.
     *
     * @param query the search query
     * @param includeEmbeddedTypes the optional embedded types to include in the show
     * @return the tv show
     * @see Show
     */
    public Show singleSearchShow(final String query, final Show.EmbeddedType... includeEmbeddedTypes) {
        final HttpUrl url = validateAndFormatSearchUrl(SINGLE_SEARCH_SHOWS_API_PATH, query, includeEmbeddedTypes);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new BasicParser<>(Show.class));
    }

    ///////////////
    // lookupShow
    ///////////////

    /**
     * Lookup a show based on an alternative external identifier.
     *
     * @param type the id type identifier
     * @param externalId the external identifier
     * @return the tv show
     * @see ShowLookupIdType
     * @see Show
     */
    public Show lookupShow(final ShowLookupIdType type, final String externalId) {
        final HttpUrl url = validateAndFormatLookupShowUrl(type, externalId);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new BasicParser<>(Show.class));
    }

    private HttpUrl validateAndFormatLookupShowUrl(@NonNull final ShowLookupIdType type, final String externalId) {
        final String formattedId = validateId(externalId);
        return HttpUrl.parse(connection.getBaseUrl() + LOOKUP_SHOWS_API_PATH)
                .newBuilder()
                .addQueryParameter(type.getQueryParameter(), formattedId)
                .build();
    }

    /////////////////
    // searchPeople
    /////////////////

    /**
     * Search for people (e.g., actors, directors, etc.).
     *
     * @param query the search query
     * @return the list of persons associated with the query
     * @see Person
     * @see PersonResult
     */
    public List<PersonResult> searchPeople(final String query) {
        final HttpUrl url = validateAndFormatSearchUrl(SEARCH_PEOPLE_API_PATH, query);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                new ListParser<>(PersonResult.class));
    }

    private HttpUrl validateAndFormatSearchUrl(
            final String apiPath,
            final String query,
            final Show.EmbeddedType... includeEmbeddedTypes) {
        Validate.notBlank(apiPath, "apiPath must not be blank");

        final String formattedQuery = validateQuery(query);
        final HttpUrl.Builder urlBuilder = HttpUrl.parse(connection.getBaseUrl() + apiPath)
                .newBuilder()
                .addQueryParameter("q", formattedQuery);
        final HttpUrl url = formatEmbeddedTypes(urlBuilder, includeEmbeddedTypes).build();
        if (log.isDebugEnabled()) {
            log.debug("Search URL: {}", url);
        }
        return url;
    }

    protected static String validateQuery(final String query) {
        Validate.notBlank(query, "query must not be blank");
        Validate.isTrue(query.length() <= MAX_QUERY_LENGTH,
                "query length must be <= " + MAX_QUERY_LENGTH);

        return query;
    }

    /** The type of alternative identifier type. */
    @RequiredArgsConstructor
    public enum ShowLookupIdType {
        IMDB("imdb"),
        TV_RAGE("tvrage"),
        TVDB("thetvdb");

        @Getter
        private final String queryParameter;
    }
}
