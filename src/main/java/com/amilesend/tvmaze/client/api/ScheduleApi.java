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
import com.amilesend.tvmaze.client.parse.adapters.LocalDateTypeAdapter;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.amilesend.tvmaze.client.parse.parser.Parsers.EPISODE_LIST_PARSER;

/**
 * TVMaze API to retrieve schedule information.
 * <br/>
 * For more information, please refer to <a href="https://www.tvmaze.com/api#schedule">
 * https://www.tvmaze.com/api#schedule</a>
 */
public class ScheduleApi extends ApiBase {
    private static final String SCHEDULE_API_PATH = "/schedule";
    private static final String WEB_SCHEDULE_API_PATH = SCHEDULE_API_PATH + "/web";
    private static final int MAX_COUNTRY_CODE_LENGTH = 3;
    private static final Set<String> ISO_COUNTRY_CODES = Set.of(Locale.getISOCountries());

    /**
     * Creates a new {@code ScheduleApi} object
     *
     * @param connection the connection
     */
    public ScheduleApi(final Connection connection) {
        super(connection);
    }

    ////////////////
    // getSchedule
    ////////////////

    /**
     * Retrieves the list of episodes that air in a given country and date.
     *
     * @param countryCode the ISO 3166-1 country code (e.g., "US"), or {@code null}
     * @param date the date, or {@code null} for the current date
     * @return the list of airing episodes
     */
    public List<Episode> getSchedule(final String countryCode, final LocalDate date) {
        final HttpUrl url = formatScheduleUrl(SCHEDULE_API_PATH, countryCode, date);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    /**
     * Retrieves the list of episodes that air in a given country and date.
     *
     * @param countryCode the ISO 3166-1 country code (e.g., "US"), or {@code null}
     * @param date the date, or {@code null} for the current date
     * @return the completable future to retrieve the list of airing episodes
     */
    public CompletableFuture<List<Episode>> getScheduleAsync(final String countryCode, final LocalDate date) {
        final HttpUrl url = formatScheduleUrl(SCHEDULE_API_PATH, countryCode, date);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    ////////////////////////////
    // getWebStreamingSchedule
    ////////////////////////////

    /**
     * Retrieves the list of episodes that air on web/streaming channels in a given country and date.
     *
     * @param countryCode the ISO 3166-1 country code (e.g., "US"), or {@code null}
     * @param date the date, or {@code null} for the current date
     * @return the list of airing episodes
     */
    public List<Episode> getWebStreamingSchedule(final String countryCode, final LocalDate date) {
        final HttpUrl url = formatScheduleUrl(WEB_SCHEDULE_API_PATH, countryCode, date);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    /**
     * Retrieves the list of episodes that air on web/streaming channels in a given country and date.
     *
     * @param countryCode the ISO 3166-1 country code (e.g., "US"), or {@code null}
     * @param date the date, or {@code null} for the current date
     * @return the completable future to retrieve the list of airing episodes
     */
    public CompletableFuture<List<Episode>> getWebStreamingScheduleAsync(
            final String countryCode,
            final LocalDate date) {
        final HttpUrl url = formatScheduleUrl(WEB_SCHEDULE_API_PATH, countryCode, date);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    ////////////////////
    // getFullSchedule
    ////////////////////

    /**
     * Retrieves the list of all future episodes. Note: This operation is expensive.
     *
     * @return the list of future episodes
     */
    public List<Episode> getFullSchedule() {
        return connection.execute(
                connection.newRequestBuilder()
                        .url(getFullScheduleUrl())
                        .build(),
                EPISODE_LIST_PARSER);
    }

    /**
     * Retrieves the list of all future episodes. Note: This operation is expensive.
     *
     * @return the completable future to retrieve the list of future episodes
     */
    public CompletableFuture<List<Episode>> getFullScheduleAsync() {
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(getFullScheduleUrl())
                        .build(),
                EPISODE_LIST_PARSER);
    }

    private HttpUrl getFullScheduleUrl() {
        return HttpUrl.parse(
                new StringBuilder(connection.getBaseUrl())
                        .append("/schedule/full")
                        .toString());
    }

    private HttpUrl formatScheduleUrl(final String apiPath, final String countryCode, final LocalDate date) {
        Validate.notBlank(apiPath, "apiPath must not be blank");

        final HttpUrl.Builder urlBuilder = HttpUrl.parse(
                new StringBuilder(connection.getBaseUrl())
                        .append(apiPath)
                        .toString())
                .newBuilder();
        if (StringUtils.isNotBlank(countryCode)) {
            urlBuilder.addQueryParameter("country", validateAndFormatCountryCode(countryCode));
        }
        Optional.ofNullable(date)
                .ifPresent(d -> urlBuilder.addQueryParameter("date", d.format(LocalDateTypeAdapter.FORMATTER)));

        return urlBuilder.build();
    }

    private static String validateAndFormatCountryCode(final String countryCode) {
        Validate.notBlank(countryCode, "countryCode must not be blank");
        Validate.isTrue(countryCode.length() <= MAX_COUNTRY_CODE_LENGTH,
                "countryCode length must be <= " + MAX_COUNTRY_CODE_LENGTH);
        Validate.isTrue(ISO_COUNTRY_CODES.contains(countryCode),
                "countryCode must be a valid ISO 3166-1 country code");

        return URLEncoder.encode(countryCode, StandardCharsets.UTF_8);
    }
}
