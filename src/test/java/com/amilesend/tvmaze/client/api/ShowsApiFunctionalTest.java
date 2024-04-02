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

import com.amilesend.tvmaze.client.FunctionalTestBase;
import com.amilesend.tvmaze.client.data.SerializedResource;
import com.amilesend.tvmaze.client.data.ShowTestDataValidator;
import com.amilesend.tvmaze.client.model.AlternateEpisode;
import com.amilesend.tvmaze.client.model.AlternateList;
import com.amilesend.tvmaze.client.model.Episode;
import com.amilesend.tvmaze.client.model.Image;
import com.amilesend.tvmaze.client.model.Season;
import com.amilesend.tvmaze.client.model.Show;
import com.amilesend.tvmaze.client.model.type.Alias;
import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.CrewMember;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.amilesend.tvmaze.client.data.AlternateListTestDataHelper.newAlternateEpisodeList;
import static com.amilesend.tvmaze.client.data.AlternateListTestDataHelper.newAlternateList;
import static com.amilesend.tvmaze.client.data.AlternateListTestDataHelper.newListOfAlternateList;
import static com.amilesend.tvmaze.client.data.AlternateListTestDataValidator.verifyAlternateList;
import static com.amilesend.tvmaze.client.data.AlternateListTestDataValidator.verifyListOfAlternateEpisode;
import static com.amilesend.tvmaze.client.data.AlternateListTestDataValidator.verifyListOfAlternateList;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataHelper.newEpisode;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataHelper.newListOfEpisodes;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataValidator.verifyEpisode;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataValidator.verifyListOfEpisodes;
import static com.amilesend.tvmaze.client.data.PersonTestDataHelper.newCastMembers;
import static com.amilesend.tvmaze.client.data.PersonTestDataHelper.newCrewMembers;
import static com.amilesend.tvmaze.client.data.PersonTestDataValidator.verifyCastMembers;
import static com.amilesend.tvmaze.client.data.PersonTestDataValidator.verifyCrewMembers;
import static com.amilesend.tvmaze.client.data.ShowTestDataHelper.newAliasList;
import static com.amilesend.tvmaze.client.data.ShowTestDataHelper.newImageList;
import static com.amilesend.tvmaze.client.data.ShowTestDataHelper.newListOfSeasons;
import static com.amilesend.tvmaze.client.data.ShowTestDataHelper.newShow;
import static com.amilesend.tvmaze.client.data.ShowTestDataHelper.newShowList;

public class ShowsApiFunctionalTest extends FunctionalTestBase {
    ////////////
    // getShow
    ////////////

    @Test
    public void getShow_withValidShowAndTypes_shouldReturnShow() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.ALL_EMBEDDED_TYPES);
        final Show expected = newShow(
                1,
                Show.EmbeddedType.EPISODES,
                Show.EmbeddedType.PREVIOUS_EPISODE,
                Show.EmbeddedType.NEXT_EPISODE,
                Show.EmbeddedType.CAST);

        final Show actual = getClient().getShowsApi().getShow(
                1,
                Show.EmbeddedType.EPISODES,
                Show.EmbeddedType.PREVIOUS_EPISODE,
                Show.EmbeddedType.NEXT_EPISODE,
                Show.EmbeddedType.CAST);

        ShowTestDataValidator.verifyShow(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getShowAsync_withValidShowAndTypes_shouldReturnShow() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.ALL_EMBEDDED_TYPES);
        final Show expected = newShow(
                1,
                Show.EmbeddedType.EPISODES,
                Show.EmbeddedType.PREVIOUS_EPISODE,
                Show.EmbeddedType.NEXT_EPISODE,
                Show.EmbeddedType.CAST);

        final CompletableFuture<Show> actual = getClient().getShowsApi().getShowAsync(
                1,
                Show.EmbeddedType.EPISODES,
                Show.EmbeddedType.PREVIOUS_EPISODE,
                Show.EmbeddedType.NEXT_EPISODE,
                Show.EmbeddedType.CAST);

        ShowTestDataValidator.verifyShow(expected, actual.get());
    }

    ////////////////
    // getEpisodes
    ////////////////

    @Test
    public void getEpisodes_withValidShowAndSpecialsIncluded_shouldReturnEpisodeList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST);
        final List<Episode> expected = newListOfEpisodes();

        final List<Episode> actual = getClient().getShowsApi().getEpisodes(1, true);

        verifyListOfEpisodes(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getEpisodesAsync_withValidShowAndSpecialsIncluded_shouldReturnEpisodeList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST);
        final List<Episode> expected = newListOfEpisodes();

        final CompletableFuture<List<Episode>> actual =
                getClient().getShowsApi().getEpisodesAsync(1, true);

        verifyListOfEpisodes(expected, actual.get());
    }

    //////////////////////
    // getAlternateLists
    //////////////////////

    @Test
    public void getAlternateLists_withValidShowId_shouldReturnListOfAlternateLists() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.ALTERNATE_LIST_LIST);
        final List<AlternateList> expected = newListOfAlternateList();

        final List<AlternateList> actual = getClient().getShowsApi().getAlternateLists(1);

        verifyListOfAlternateList(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getAlternateListsAsync_withValidShowId_shouldReturnListOfAlternateLists() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.ALTERNATE_LIST_LIST);
        final List<AlternateList> expected = newListOfAlternateList();

        final CompletableFuture<List<AlternateList>> actual =
                getClient().getShowsApi().getAlternateListsAsync(1);

        verifyListOfAlternateList(expected, actual.get());
    }

    /////////////////////
    // getAlternateList
    /////////////////////

    @Test
    public void getAlternateList_withValidateIdAndAlternateEpisodesIncluded_shouldReturnAlternateList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.ALTERNATE_LIST_EMBEDDED_ALTERNATE_EPISODES);
        final AlternateList expected = newAlternateList(1, AlternateList.EmbeddedType.ALTERNATE_EPISODES);

        final AlternateList actual = getClient().getShowsApi().getAlternateList(1, true);

        verifyAlternateList(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getAlternateListAsync_withValidateIdAndAlternateEpisodesIncluded_shouldReturnAlternateList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.ALTERNATE_LIST_EMBEDDED_ALTERNATE_EPISODES);
        final AlternateList expected = newAlternateList(1, AlternateList.EmbeddedType.ALTERNATE_EPISODES);

        final CompletableFuture<AlternateList> actual =
                getClient().getShowsApi().getAlternateListAsync(1, true);

        verifyAlternateList(expected, actual.get());
    }

    /////////////////////////
    // getAlternateEpisodes
    /////////////////////////

    @Test
    public void getAlternateEpisodes_withValidIdAndEpisodesIncluded_shouldReturnAlternateEpisodeList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.ALTERNATE_EPISODES_LIST);
        final List<AlternateEpisode> expected = newAlternateEpisodeList(true);

        final List<AlternateEpisode> actual =
                getClient().getShowsApi().getAlternateEpisodes(1, true);

        verifyListOfAlternateEpisode(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getAlternateEpisodesAsync_withValidIdAndEpisodesIncluded_shouldReturnAlternateEpisodeList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.ALTERNATE_EPISODES_LIST);
        final List<AlternateEpisode> expected = newAlternateEpisodeList(true);

        final CompletableFuture<List<AlternateEpisode>> actual =
                getClient().getShowsApi().getAlternateEpisodesAsync(1, true);

        verifyListOfAlternateEpisode(expected, actual.get());
    }

    ///////////////
    // getEpisode
    ///////////////

    @Test
    public void getEpisode_withValidIdAndNumbers_shouldReturnEpisode() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.EPISODE);
        final Episode expected = newEpisode(1, 1, 1, (Episode.EmbeddedType) null);

        final Episode actual = getClient().getShowsApi().getEpisode(1, 1, 1);

        verifyEpisode(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getEpisodeAsync_withValidIdAndNumbers_shouldReturnEpisode() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.EPISODE);
        final Episode expected = newEpisode(1, 1, 1, (Episode.EmbeddedType) null);

        final CompletableFuture<Episode> actual = getClient().getShowsApi().getEpisodeAsync(1, 1, 1);

        verifyEpisode(expected, actual.get());
    }

    ////////////////
    // getEpisodes
    ////////////////

    @Test
    public void getEpisodes_withValidShowIdAndDate_shouldReturnEpisodeList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST);
        final List<Episode> expected = newListOfEpisodes();

        final List<Episode> actual = getClient().getShowsApi().getEpisodes(1, LocalDate.of(2020, 1, 15));

        verifyListOfEpisodes(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getEpisodesAsync_withValidShowIdAndDate_shouldReturnEpisodeList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST);
        final List<Episode> expected = newListOfEpisodes();

        final CompletableFuture<List<Episode>> actual =
                getClient().getShowsApi().getEpisodesAsync(1, LocalDate.of(2020, 1, 15));

        verifyListOfEpisodes(expected, actual.get());
    }

    ///////////////
    // getSeasons
    ///////////////

    @Test
    public void getSeasons_withValidShowId_shouldReturnListOfSeasons() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.SEASON_LIST);
        final List<Season> expected = newListOfSeasons();

        final List<Season> actual = getClient().getShowsApi().getSeasons(1);

        ShowTestDataValidator.verifyListOfSeasons(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getSeasonsAsync_withValidShowId_shouldReturnListOfSeasons() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.SEASON_LIST);
        final List<Season> expected = newListOfSeasons();

        final CompletableFuture<List<Season>> actual = getClient().getShowsApi().getSeasonsAsync(1);

        ShowTestDataValidator.verifyListOfSeasons(expected, actual.get());
    }

    //////////////////////
    // getSeasonEpisodes
    //////////////////////

    @Test
    public void getSeasonEpisodes_withValidSeasonIdAndGuestCastIncluded_shouldReturnListOfEpisodes() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST_EMBEDDED_GUEST_CAST);
        final List<Episode> expected = newListOfEpisodes(Episode.EmbeddedType.GUEST_CAST);

        final List<Episode> actual = getClient().getShowsApi().getSeasonEpisodes(1, true);

        verifyListOfEpisodes(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getSeasonEpisodesAsync_withValidSeasonIdAndGuestCastIncluded_shouldReturnListOfEpisodes() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST_EMBEDDED_GUEST_CAST);
        final List<Episode> expected = newListOfEpisodes(Episode.EmbeddedType.GUEST_CAST);

        final CompletableFuture<List<Episode>> actual =
                getClient().getShowsApi().getSeasonEpisodesAsync(1, true);

        verifyListOfEpisodes(expected, actual.get());
    }

    ////////////
    // getCast
    ////////////

    @Test
    public void getCast_withValidShowId_shouldReturnListOfCastMembers() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CAST_MEMBER_LIST);
        final List<CastMember> expected = newCastMembers();

        final List<CastMember> actual = getClient().getShowsApi().getCast(1);

        verifyCastMembers(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getCastAsync_withValidShowId_shouldReturnListOfCastMembers() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CAST_MEMBER_LIST);
        final List<CastMember> expected = newCastMembers();

        final CompletableFuture<List<CastMember>> actual = getClient().getShowsApi().getCastAsync(1);

        verifyCastMembers(expected, actual.get());
    }

    ////////////
    // getCrew
    ////////////

    @Test
    public void getCrew_withValidShowId_shouldReturnListOfCrewMembers() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CREW_MEMBER_LIST);
        final List<CrewMember> expected = newCrewMembers();

        final List<CrewMember> actual = getClient().getShowsApi().getCrew(1);

        verifyCrewMembers(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getCrewAsync_withValidShowId_shouldReturnListOfCrewMembers() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CREW_MEMBER_LIST);
        final List<CrewMember> expected = newCrewMembers();

        final CompletableFuture<List<CrewMember>> actual = getClient().getShowsApi().getCrewAsync(1);

        verifyCrewMembers(expected, actual.get());
    }

    ///////////////
    // getAliases
    ///////////////

    @Test
    public void getAliases_withValidShowId_shouldReturnListOfAlias() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.ALIAS_LIST);
        final List<Alias> expected = newAliasList();

        final List<Alias> actual = getClient().getShowsApi().getAliases(1);

        ShowTestDataValidator.verifyAliasList(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getAliasesAsync_withValidShowId_shouldReturnListOfAlias() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.ALIAS_LIST);
        final List<Alias> expected = newAliasList();

        final CompletableFuture<List<Alias>> actual = getClient().getShowsApi().getAliasesAsync(1);

        ShowTestDataValidator.verifyAliasList(expected, actual.get());
    }

    //////////////
    // getImages
    //////////////

    @Test
    public void getImages_withValidShowId_shouldReturnImageList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.IMAGE_LIST);
        final List<Image> expected = newImageList();

        final List<Image> actual = getClient().getShowsApi().getImages(1);

        ShowTestDataValidator.verifyImageList(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getImagesAsync_withValidShowId_shouldReturnImageList() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.IMAGE_LIST);
        final List<Image> expected = newImageList();

        final CompletableFuture<List<Image>> actual = getClient().getShowsApi().getImagesAsync(1);

        ShowTestDataValidator.verifyImageList(expected, actual.get());
    }

    /////////////
    // getIndex
    /////////////

    @Test
    public void getIndex_withValidPageNumber_shouldReturnListOfShows() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.LIST);
        final List<Show> expected = newShowList();

        final List<Show> actual = getClient().getShowsApi().getIndex(1);

        ShowTestDataValidator.verifyShowList(expected, actual);
    }

    @Test
    @SneakyThrows
    public void getIndexAsync_withValidPageNumber_shouldReturnListOfShows() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.LIST);
        final List<Show> expected = newShowList();

        final CompletableFuture<List<Show>> actual = getClient().getShowsApi().getIndexAsync(1);

        ShowTestDataValidator.verifyShowList(expected, actual.get());
    }
}
