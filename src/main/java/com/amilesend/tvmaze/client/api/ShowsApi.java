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
import com.amilesend.tvmaze.client.model.AlternateEpisode;
import com.amilesend.tvmaze.client.model.AlternateList;
import com.amilesend.tvmaze.client.model.Episode;
import com.amilesend.tvmaze.client.model.Image;
import com.amilesend.tvmaze.client.model.Season;
import com.amilesend.tvmaze.client.model.Show;
import com.amilesend.tvmaze.client.model.type.Alias;
import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.CrewMember;
import com.amilesend.tvmaze.client.parse.adapters.LocalDateTypeAdapter;
import lombok.NonNull;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.amilesend.tvmaze.client.parse.parser.Parsers.ALIAS_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.ALTERNATE_EPISODE_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.ALTERNATE_LIST_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.ALTERNATE_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.CAST_MEMBER_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.CREW_MEMBER_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.EPISODE_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.EPISODE_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.IMAGE_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.SEASON_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.SHOW_LIST_PARSER;
import static com.amilesend.tvmaze.client.parse.parser.Parsers.SHOW_PARSER;

/**
 * TVMaze API to retrieve show information.
 * <br/>
 * For more information, please refer to <a href="https://www.tvmaze.com/api#shows">https://www.tvmaze.com/api#shows</a>
 */
public class ShowsApi extends ApiBase {
    private static final String SHOWS_INDEX_API_PATH = "/shows";
    private static final String SHOWS_API_PATH = SHOWS_INDEX_API_PATH + "/";
    private static final String ALTERNATE_LISTS_API_PATH = "/alternatelists/";
    private static final String SEASONS_API_PATH = "/seasons/";
    private static final String ALTERNATE_EPISODES_SUB_API_PATH = "/alternateepisodes";
    private static final String ALTERNATE_LISTS_SUB_API_PATH = "/alternatelists";
    private static final String SEASONS_SUB_API_PATH = "/seasons";
    private static final String EPISODES_SUB_API_PATH = "/episodes";
    private static final String CAST_SUB_API_PATH = "/cast";
    private static final String CREW_SUB_API_PATH = "/crew";
    private static final String ALIASES_SUB_API_PATH = "/akas";
    private static final String IMAGES_SUB_API_PATH = "/images";

    /**
     * Creates a new {@code ShowsApi} object.
     *
     * @param connection the connection
     */
    public ShowsApi(final Connection connection) {
        super(connection);
    }

    ////////////
    // getShow
    ////////////

    /**
     * Gets the show for the given {@code showId}.
     *
     * @param showId the show identifier
     * @param includeEmbeddedTypes the optional embedded types to include in the show
     * @return the show
     * @see Show
     */
    public Show getShow(final int showId, final Show.EmbeddedType... includeEmbeddedTypes) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, StringUtils.EMPTY, includeEmbeddedTypes);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                SHOW_PARSER);
    }

    /**
     * Gets the show for the given {@code showId}.
     *
     * @param showId the show identifier
     * @param includeEmbeddedTypes the optional embedded types to include in the show
     * @return the completable future that retrieves the show
     * @see Show
     */
    public CompletableFuture<Show> getShowAsync(final int showId, final Show.EmbeddedType... includeEmbeddedTypes) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, StringUtils.EMPTY, includeEmbeddedTypes);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                SHOW_PARSER);
    }

    ////////////////
    // getEpisodes
    ////////////////

    /**
     * Gets the list of episodes for the given {@code showId}.
     *
     * @param showId the show identifier
     * @param isSpecialsIncluded if {@code true}, include specials in the list; else {@code false}
     * @return the list of episodes
     */
    public List<Episode> getEpisodes(final int showId, final boolean isSpecialsIncluded) {
        final HttpUrl url = validateAndFormatEpisodesUrl(showId, isSpecialsIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    /**
     * Gets the list of episodes for the given {@code showId}.
     *
     * @param showId the show identifier
     * @param isSpecialsIncluded if {@code true}, include specials in the list; else {@code false}
     * @return the completable future that retrieves the list of episodes
     */
    public CompletableFuture<List<Episode>> getEpisodesAsync(final int showId, final boolean isSpecialsIncluded) {
        final HttpUrl url = validateAndFormatEpisodesUrl(showId, isSpecialsIncluded);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    private HttpUrl validateAndFormatEpisodesUrl(final int showId, final boolean isSpecialsIncluded) {
        final String formattedId = validateId(showId);
        final HttpUrl.Builder urlBuilder = HttpUrl.parse(
                        new StringBuilder(connection.getBaseUrl())
                                .append(SHOWS_API_PATH)
                                .append(formattedId)
                                .append("/episodes")
                                .toString())
                .newBuilder();
        if (isSpecialsIncluded) {
            urlBuilder.addQueryParameter("specials", "1");
        }

        return urlBuilder.build();
    }

    //////////////////////
    // getAlternateLists
    //////////////////////

    /**
     * Gets the list of alternate episode lists for the given {@code showId} (e.g., DVD ordering).
     *
     * @param showId the show identifier
     * @return the list of alternate episodes lists
     * @see AlternateList
     */
    public List<AlternateList> getAlternateLists(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, ALTERNATE_LISTS_SUB_API_PATH);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                ALTERNATE_LIST_LIST_PARSER);
    }

    /**
     * Gets the list of alternate episode lists for the given {@code showId} (e.g., DVD ordering).
     *
     * @param showId the show identifier
     * @return the completable future that retrieves the list of alternate episodes lists
     * @see AlternateList
     */
    public CompletableFuture<List<AlternateList>> getAlternateListsAsync(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, ALTERNATE_LISTS_SUB_API_PATH);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                ALTERNATE_LIST_LIST_PARSER);
    }

    /////////////////////
    // getAlternateList
    /////////////////////

    /**
     * Gets the alternate episode list for the given {@code alternateListId}.
     *
     * @param alternateListId the alternate episode list
     * @param isAlternateEpisodesIncluded if {@code true}, includes the list of embedded alternate episodes; else,
     *                                    {@code false}
     * @return the alternate episode list
     * @see AlternateList
     */
    public AlternateList getAlternateList(final int alternateListId, final boolean isAlternateEpisodesIncluded) {
        final HttpUrl url = validateAndFormatAlternateListsUrl(alternateListId, isAlternateEpisodesIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                ALTERNATE_LIST_PARSER);
    }

    /**
     * Gets the alternate episode list for the given {@code alternateListId}.
     *
     * @param alternateListId the alternate episode list
     * @param isAlternateEpisodesIncluded if {@code true}, includes the list of embedded alternate episodes; else,
     *                                    {@code false}
     * @return the completable future that retrieves the alternate episode list
     * @see AlternateList
     */
    public CompletableFuture<AlternateList> getAlternateListAsync(
            final int alternateListId,
            final boolean isAlternateEpisodesIncluded) {
        final HttpUrl url = validateAndFormatAlternateListsUrl(alternateListId, isAlternateEpisodesIncluded);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                ALTERNATE_LIST_PARSER);
    }

    private HttpUrl validateAndFormatAlternateListsUrl(
            final int alternateListId,
            final boolean isAlternateEpisodesIncluded) {
        return isAlternateEpisodesIncluded
                ? validateAndFormatUrl(
                        ALTERNATE_LISTS_API_PATH,
                        alternateListId,
                        StringUtils.EMPTY,
                        AlternateList.EmbeddedType.ALTERNATE_EPISODES)
                : validateAndFormatUrl(ALTERNATE_LISTS_API_PATH, alternateListId, StringUtils.EMPTY);
    }

    /////////////////////////
    // getAlternateEpisodes
    /////////////////////////

    /**
     * Gets the list of alternate episodes for the given {@code alternateListId}.
     *
     * @param alternateListId the alternate list identifier
     * @param isEpisodesIncluded if {@code true}, includes the associated {@link Episode}; else, {@code false}
     * @return the list of alternate episodes
     * @see AlternateEpisode
     */
    public List<AlternateEpisode> getAlternateEpisodes(final int alternateListId, final boolean isEpisodesIncluded) {
        final HttpUrl url = validateAndFormatAlternateEpisodesUrl(alternateListId, isEpisodesIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                ALTERNATE_EPISODE_LIST_PARSER);
    }

    /**
     * Gets the list of alternate episodes for the given {@code alternateListId}.
     *
     * @param alternateListId the alternate list identifier
     * @param isEpisodesIncluded if {@code true}, includes the associated {@link Episode}; else, {@code false}
     * @return the completable future that retrieves the list of alternate episodes
     * @see AlternateEpisode
     */
    public CompletableFuture<List<AlternateEpisode>> getAlternateEpisodesAsync(
            final int alternateListId,
            final boolean isEpisodesIncluded) {
        final HttpUrl url = validateAndFormatAlternateEpisodesUrl(alternateListId, isEpisodesIncluded);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                ALTERNATE_EPISODE_LIST_PARSER);
    }

    private HttpUrl validateAndFormatAlternateEpisodesUrl(
            final int alternateListId,
            final boolean isEpisodesIncluded) {
        return isEpisodesIncluded
                ? validateAndFormatUrl(
                        ALTERNATE_LISTS_API_PATH,
                        alternateListId,
                        ALTERNATE_EPISODES_SUB_API_PATH,
                        AlternateList.EmbeddedType.EPISODES)
                : validateAndFormatUrl(ALTERNATE_LISTS_API_PATH, alternateListId, ALTERNATE_EPISODES_SUB_API_PATH);
    }

    ///////////////
    // getEpisode
    ///////////////

    /**
     * Gets an {@code Episode} for the given show, season, and episode number.
     *
     * @param showId the show identifier
     * @param seasonNum the season number
     * @param episodeNum the associated episode number for the season
     * @return the episode
     * @see Episode
     */
    public Episode getEpisode(final int showId, final int seasonNum, final int episodeNum) {
        final HttpUrl url = validateAndFormatEpisodeUrl(showId, seasonNum, episodeNum);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_PARSER);
    }

    /**
     * Gets an {@code Episode} for the given show, season, and episode number.
     *
     * @param showId the show identifier
     * @param seasonNum the season number
     * @param episodeNum the associated episode number for the season
     * @return the completable future that retrieves the episode
     * @see Episode
     */
    public CompletableFuture<Episode> getEpisodeAsync(final int showId, final int seasonNum, final int episodeNum) {
        final HttpUrl url = validateAndFormatEpisodeUrl(showId, seasonNum, episodeNum);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_PARSER);
    }

    private HttpUrl validateAndFormatEpisodeUrl(
            final int showId,
            final int seasonNum,
            final int episodeNum) {
        final String formattedShowId = validateId(showId);
        final String formattedSeasonNum = validateId(seasonNum);
        final String formattedEpisodeNum = validateId(episodeNum);
        return HttpUrl.parse(
                        new StringBuilder(connection.getBaseUrl())
                                .append(SHOWS_API_PATH)
                                .append(formattedShowId)
                                .append("/episodebynumber")
                                .toString())
                .newBuilder()
                .addQueryParameter("season", formattedSeasonNum)
                .addQueryParameter("number", formattedEpisodeNum)
                .build();
    }

    ////////////////
    // getEpisodes
    ////////////////

    /**
     * Gets the list of episodes for a show that aired on the given date.
     *
     * @param showId the show identifier
     * @param date the date of airing
     * @return the list of episodes
     * @see Episode
     */
    public List<Episode> getEpisodes(final int showId, final LocalDate date) {
        final HttpUrl url = validateAndFormatEpisodesUrl(showId, date);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    /**
     * Gets the list of episodes for a show that aired on the given date.
     *
     * @param showId the show identifier
     * @param date the date of airing
     * @return the completable future that retrieves the list of episodes
     * @see Episode
     */
    public CompletableFuture<List<Episode>> getEpisodesAsync(final int showId, final LocalDate date) {
        final HttpUrl url = validateAndFormatEpisodesUrl(showId, date);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    private HttpUrl validateAndFormatEpisodesUrl(final int showId, final LocalDate date) {
        final String formattedShowId = validateId(showId);
        final String formattedDate = validateAndFormatDate(date);
        return HttpUrl.parse(
                        new StringBuilder(connection.getBaseUrl())
                                .append("/shows/")
                                .append(formattedShowId)
                                .append("/episodesbydate")
                                .toString())
                .newBuilder()
                .addQueryParameter("date", formattedDate)
                .build();
    }

    ///////////////
    // getSeasons
    ///////////////

    /**
     * Gets the list of seasons for a show.
     *
     * @param showId the show identifier
     * @return the list of seasons
     * @see Season
     */
    public List<Season> getSeasons(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, SEASONS_SUB_API_PATH);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                SEASON_LIST_PARSER);
    }

    /**
     * Gets the list of seasons for a show.
     *
     * @param showId the show identifier
     * @return the completable future that retrieves the list of seasons
     * @see Season
     */
    public CompletableFuture<List<Season>> getSeasonsAsync(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, SEASONS_SUB_API_PATH);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                SEASON_LIST_PARSER);
    }

    //////////////////////
    // getSeasonEpisodes
    //////////////////////

    /**
     * Gets the list of episodes for a given {@code seasonId}.
     *
     * @param seasonId the season identifier
     * @param isGuestCastIncluded if {@code true}, returns the list of guest cast members; else {@code false}
     * @return the list of episodes
     * @see Episode
     */
    public List<Episode> getSeasonEpisodes(final int seasonId, final boolean isGuestCastIncluded) {
        final HttpUrl url = validateAndFormatSeasonEpisodesUrl(seasonId, isGuestCastIncluded);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    /**
     * Gets the list of episodes for a given {@code seasonId}.
     *
     * @param seasonId the season identifier
     * @param isGuestCastIncluded if {@code true}, returns the list of guest cast members; else {@code false}
     * @return the completable future that retrieves the list of episodes
     * @see Episode
     */
    public CompletableFuture<List<Episode>> getSeasonEpisodesAsync(
            final int seasonId,
            final boolean isGuestCastIncluded) {
        final HttpUrl url = validateAndFormatSeasonEpisodesUrl(seasonId, isGuestCastIncluded);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                EPISODE_LIST_PARSER);
    }

    private HttpUrl validateAndFormatSeasonEpisodesUrl(final int seasonId, final boolean isGuestCastIncluded) {
        return isGuestCastIncluded
                ? validateAndFormatUrl(
                        SEASONS_API_PATH,
                        seasonId,
                        EPISODES_SUB_API_PATH,
                        Episode.EmbeddedType.GUEST_CAST)
                : validateAndFormatUrl(SEASONS_API_PATH, seasonId, EPISODES_SUB_API_PATH);
    }

    ////////////
    // getCast
    ////////////

    /**
     * Gets the list of cast members for a show.
     *
     * @param showId the show identifier
     * @return the list of cast members
     * @see CastMember
     */
    public List<CastMember> getCast(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, CAST_SUB_API_PATH);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CAST_MEMBER_LIST_PARSER);
    }

    /**
     * Gets the list of cast members for a show.
     *
     * @param showId the show identifier
     * @return the completable future that retrieves the list of cast members
     * @see CastMember
     */
    public CompletableFuture<List<CastMember>> getCastAsync(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, CAST_SUB_API_PATH);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CAST_MEMBER_LIST_PARSER);
    }

    ////////////
    // getCrew
    ////////////

    /**
     * Gets the list of crew members for a show.
     *
     * @param showId the show identifier
     * @return the list of crew members
     * @see CrewMember
     */
    public List<CrewMember> getCrew(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, CREW_SUB_API_PATH);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CREW_MEMBER_LIST_PARSER);
    }

    /**
     * Gets the list of crew members for a show.
     *
     * @param showId the show identifier
     * @return the completable future that retrieves the list of crew members
     * @see CrewMember
     */
    public CompletableFuture<List<CrewMember>> getCrewAsync(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, CREW_SUB_API_PATH);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                CREW_MEMBER_LIST_PARSER);
    }

    ///////////////
    // getAliases
    ///////////////

    /**
     * Gets the list of alternative show names, or aliases.
     *
     * @param showId the show identifier
     * @return the list of aliases
     * @see Alias
     */
    public List<Alias> getAliases(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, ALIASES_SUB_API_PATH);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                ALIAS_LIST_PARSER);
    }

    /**
     * Gets the list of alternative show names, or aliases.
     *
     * @param showId the show identifier
     * @return the completable future that retrieves the list of aliases
     * @see Alias
     */
    public CompletableFuture<List<Alias>> getAliasesAsync(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, ALIASES_SUB_API_PATH);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                ALIAS_LIST_PARSER);
    }

    //////////////
    // getImages
    //////////////

    /**
     * Gets the list of images for a show.
     *
     * @param showId the show identifier
     * @return the list of images
     * @see Image
     */
    public List<Image> getImages(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, IMAGES_SUB_API_PATH);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                IMAGE_LIST_PARSER);
    }

    /**
     * Gets the list of images for a show.
     *
     * @param showId the show identifier
     * @return the completable future that retrieves the list of images
     * @see Image
     */
    public CompletableFuture<List<Image>> getImagesAsync(final int showId) {
        final HttpUrl url = validateAndFormatUrl(SHOWS_API_PATH, showId, IMAGES_SUB_API_PATH);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                IMAGE_LIST_PARSER);
    }

    /////////////
    // getIndex
    /////////////

    /**
     * Gets the list of all shows in the TVMaze database. Note: This is paginated and requires manual specification
     * of the page number with a maximum of 250 shows per response. This operation will throw a {@link RequestException}
     * when no more pages exist.
     *
     * @param pageNum the page number
     * @return the list of shows
     * @throws RequestException if there are no more shows to return
     */
    public List<Show> getIndex(final int pageNum) {
        final HttpUrl url = validateAndFormatIndexUrl(SHOWS_INDEX_API_PATH, pageNum);
        return connection.execute(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                SHOW_LIST_PARSER);
    }

    /**
     * Gets the list of all shows in the TVMaze database. Note: This is paginated and requires manual specification
     * of the page number with a maximum of 250 shows per response. This operation will throw a {@link RequestException}
     * when no more pages exist.
     *
     * @param pageNum the page number
     * @return the completable future that retrieves the list of shows
     * @throws RequestException if there are no more shows to return
     */
    public CompletableFuture<List<Show>> getIndexAsync(final int pageNum) {
        final HttpUrl url = validateAndFormatIndexUrl(SHOWS_INDEX_API_PATH, pageNum);
        return connection.executeAsync(
                connection.newRequestBuilder()
                        .url(url)
                        .build(),
                SHOW_LIST_PARSER);
    }

    private static String validateAndFormatDate(@NonNull final LocalDate date) {
        return URLEncoder.encode(date.format(LocalDateTypeAdapter.FORMATTER), StandardCharsets.UTF_8);
    }
}
