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
import com.amilesend.tvmaze.client.connection.RequestException;
import com.amilesend.tvmaze.client.model.CastCredit;
import com.amilesend.tvmaze.client.model.CrewCredit;
import com.amilesend.tvmaze.client.model.Person;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.amilesend.tvmaze.client.parse.parser.Parsers.CAST_CREDIT_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.CREW_CREDIT_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.PERSON_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.PERSON_PARSER;

/**
 * TVMaze API to retrieve people information.
 * <br/>
 * For more information, please refer to <a href="https://www.tvmaze.com/api#people">
 * https://www.tvmaze.com/api#people</a>
 */
public class PeopleApi extends ApiBase {
    private static final String PEOPLE_INDEX_PATH = "/people";
    private static final String PEOPLE_API_PATH = PEOPLE_INDEX_PATH + "/";
    private static final String CAST_CREDITS_SUB_API_PATH = "/castcredits";
    private static final String CREW_CREDITS_SUB_API_PATH = "/crewcredits";
    private static final String GUEST_CAST_CREDITS_SUB_API_PATH = "/guestcastcredits";

    /**
     * Creates a new {@code PeopleApi} object.
     *
     * @param connection the connection
     */
    public PeopleApi(final Connection connection) {
        super(connection);
    }


    //////////////
    // getPerson
    //////////////

    /**
     * Retrieves information for a specific person.
     *
     * @param personId the person identifier
     * @param isCastCreditsIncluded if {@code true}, include cast credits in the response; else, {@code false}
     * @return the person
     * @see Person
     * @see CastCredit
     */
    public Person getPerson(final int personId, final boolean isCastCreditsIncluded) {
        final HttpUrl url = validateAndFormatPeopleUrl(personId, isCastCreditsIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                PERSON_PARSER);
    }

    /**
     * Retrieves information for a specific person.
     *
     * @param personId the person identifier
     * @param isCastCreditsIncluded if {@code true}, include cast credits in the response; else, {@code false}
     * @return the completable future that retrieves the person
     * @see Person
     * @see CastCredit
     */
    public CompletableFuture<Person> getPersonAsync(final int personId, final boolean isCastCreditsIncluded) {
        final HttpUrl url = validateAndFormatPeopleUrl(personId, isCastCreditsIncluded);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                PERSON_PARSER);
    }

    private HttpUrl validateAndFormatPeopleUrl(final int personId, final boolean isCastCreditsIncluded) {
        return isCastCreditsIncluded
                ? validateAndFormatUrl(PEOPLE_API_PATH, personId, StringUtils.EMPTY, Person.EmbeddedType.CAST_CREDITS)
                : validateAndFormatUrl(PEOPLE_API_PATH, personId, StringUtils.EMPTY);
    }

    ///////////////////
    // getCastCredits
    ///////////////////

    /**
     * Retrieves the list of show-level cast credits for a specific person.
     *
     * @param personId the person identifier
     * @param isShowIncluded if {@code true}, include show information in the response; else, {@code false}
     * @return the list of cast credits
     * @see CastCredit
     * @see com.amilesend.tvmaze.client.model.Show
     */
    public List<CastCredit> getCastCredits(final int personId, final boolean isShowIncluded) {
        final HttpUrl url = validateAndFormatCastCreditsUrl(personId, isShowIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CAST_CREDIT_LIST_PARSER);
    }

    /**
     * Retrieves the list of show-level cast credits for a specific person.
     *
     * @param personId the person identifier
     * @param isShowIncluded if {@code true}, include show information in the response; else, {@code false}
     * @return the completable future that retrieves the list of cast credits
     * @see CastCredit
     * @see com.amilesend.tvmaze.client.model.Show
     */
    public CompletableFuture<List<CastCredit>> getCastCreditsAsync(final int personId, final boolean isShowIncluded) {
        final HttpUrl url = validateAndFormatCastCreditsUrl(personId, isShowIncluded);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CAST_CREDIT_LIST_PARSER);
    }

    private HttpUrl validateAndFormatCastCreditsUrl(final int personId, final boolean isShowIncluded) {
        return isShowIncluded
                ? validateAndFormatUrl(
                        PEOPLE_API_PATH,
                        personId,
                        CAST_CREDITS_SUB_API_PATH,
                        CastCredit.EmbeddedType.SHOW)
                : validateAndFormatUrl(PEOPLE_API_PATH, personId, CAST_CREDITS_SUB_API_PATH);
    }

    ///////////////////
    // getCrewCredits
    ///////////////////

    /**
     * Retrieves the list of show-level crew credits for a specific person.
     *
     * @param personId the person identifier
     * @param isShowIncluded if {@code true}, include show information in the response; else, {@code false}
     * @return the list of crew credits
     * @see CrewCredit
     * @see com.amilesend.tvmaze.client.model.Show
     */
    public List<CrewCredit> getCrewCredits(final int personId, final boolean isShowIncluded) {
        final HttpUrl url = validateAndFormatCrewCreditsUrl(personId, isShowIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CREW_CREDIT_LIST_PARSER);
    }

    /**
     * Retrieves the list of show-level crew credits for a specific person.
     *
     * @param personId the person identifier
     * @param isShowIncluded if {@code true}, include show information in the response; else, {@code false}
     * @return the completable future that retrieves the list of crew credits
     * @see CrewCredit
     * @see com.amilesend.tvmaze.client.model.Show
     */
    public CompletableFuture<List<CrewCredit>> getCrewCreditsAsync(final int personId, final boolean isShowIncluded) {
        final HttpUrl url = validateAndFormatCrewCreditsUrl(personId, isShowIncluded);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CREW_CREDIT_LIST_PARSER);
    }

    private HttpUrl validateAndFormatCrewCreditsUrl(final int personId, final boolean isShowIncluded) {
        return isShowIncluded
                ? validateAndFormatUrl(
                        PEOPLE_API_PATH,
                        personId,
                        CREW_CREDITS_SUB_API_PATH,
                        CastCredit.EmbeddedType.SHOW)
                : validateAndFormatUrl(PEOPLE_API_PATH, personId, CREW_CREDITS_SUB_API_PATH);
    }

    ////////////////////////
    // getGuestCastCredits
    ////////////////////////

    /**
     * Retrieves the list of episode-level guest cast credits for a specific person.
     *
     * @param personId the person identifier
     * @param isEpisodeIncluded if {@code true}, include episode information in the response; else, {@code false}
     * @return the list of guest cast credits
     * @see CastCredit
     * @see com.amilesend.tvmaze.client.model.Episode
     */
    public List<CastCredit> getGuestCastCredits(final int personId, final boolean isEpisodeIncluded) {
        final HttpUrl url = validateAndFormatGuestCastCreditsUrl(personId, isEpisodeIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CAST_CREDIT_LIST_PARSER);
    }

    /**
     * Retrieves the list of episode-level guest cast credits for a specific person.
     *
     * @param personId the person identifier
     * @param isEpisodeIncluded if {@code true}, include episode information in the response; else, {@code false}
     * @return the completable future that retrieves the list of guest cast credits
     * @see CastCredit
     * @see com.amilesend.tvmaze.client.model.Episode
     */
    public CompletableFuture<List<CastCredit>> getGuestCastCreditsAsync(
            final int personId,
            final boolean isEpisodeIncluded) {
        final HttpUrl url = validateAndFormatGuestCastCreditsUrl(personId, isEpisodeIncluded);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CAST_CREDIT_LIST_PARSER);
    }

    private HttpUrl validateAndFormatGuestCastCreditsUrl(final int personId, final boolean isEpisodeIncluded) {
        return isEpisodeIncluded
                ? validateAndFormatUrl(
                        PEOPLE_API_PATH,
                        personId,
                        GUEST_CAST_CREDITS_SUB_API_PATH,
                        CastCredit.EmbeddedType.EPISODE)
                : validateAndFormatUrl(PEOPLE_API_PATH, personId, GUEST_CAST_CREDITS_SUB_API_PATH);
    }

    /////////////
    // getIndex
    /////////////

    /**
     * Gets the list of all persons in the TVMaze database. Note: This is paginated and requires manual specification
     * of the page number with a maximum of 250 shows per response. This operation will throw a {@link RequestException}
     * when no more pages exist.
     *
     * @param pageNum the page number
     * @return the list of persons
     * @throws RequestException if there are no more persons to return
     * @see Person
     */
    public List<Person> getIndex(final int pageNum) {
        final HttpUrl url = validateAndFormatIndexUrl(PEOPLE_INDEX_PATH, pageNum);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                PERSON_LIST_PARSER);
    }

    /**
     * Gets the list of all persons in the TVMaze database. Note: This is paginated and requires manual specification
     * of the page number with a maximum of 250 shows per response. This operation will throw a {@link RequestException}
     * when no more pages exist.
     *
     * @param pageNum the page number
     * @return the completable future that retrieves the list of persons
     * @throws RequestException if there are no more persons to return
     * @see Person
     */
    public CompletableFuture<List<Person>> getIndexAsync(final int pageNum) {
        final HttpUrl url = validateAndFormatIndexUrl(PEOPLE_INDEX_PATH, pageNum);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                PERSON_LIST_PARSER);
    }
 }
