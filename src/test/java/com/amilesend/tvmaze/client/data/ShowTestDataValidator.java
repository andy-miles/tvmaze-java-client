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
package com.amilesend.tvmaze.client.data;

import com.amilesend.tvmaze.client.model.Image;
import com.amilesend.tvmaze.client.model.Season;
import com.amilesend.tvmaze.client.model.Show;
import com.amilesend.tvmaze.client.model.type.ShowResult;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;

import static com.amilesend.tvmaze.client.data.DataValidatorHelper.validateListOf;
import static com.amilesend.tvmaze.client.data.DataValidatorHelper.validateResource;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataValidator.verifyEpisode;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataValidator.verifyListOfEpisodes;
import static com.amilesend.tvmaze.client.data.PersonTestDataValidator.verifyCastMembers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@UtilityClass
public class ShowTestDataValidator {
    public static void verifyListOfSeasons(final List<Season> expected, final List<Season> actual) {
        validateListOf(expected, actual, ShowTestDataValidator::verifySeason);
    }

    public static void verifySeason(final Season expected, final Season actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.getNumber(), actual.getNumber()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getEpisodeOrder(), actual.getEpisodeOrder()),
                () -> assertEquals(expected.getPremiereDate(), actual.getPremiereDate()),
                () -> assertEquals(expected.getEndDate(), actual.getEndDate()),
                () -> assertEquals(expected.getNetwork(), actual.getNetwork()),
                () -> assertEquals(expected.getImage(), actual.getImage()));
    }

    public static void verifyImageList(final List<Image> expected, final List<Image> actual) {
        validateListOf(expected, actual, ShowTestDataValidator::verifyImage);
    }

    private static void verifyImage(final Image expected, final Image actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> validateResource(expected, actual),
                () -> assertEquals(expected.getType(), actual.getType()),
                () -> assertEquals(expected.getResolutions(), actual.getResolutions()));
    }

    public static void verifyShowResultList(final List<ShowResult> expected, final List<ShowResult> actual) {
        validateListOf(expected, actual, ShowTestDataValidator::verifyShowResult);
    }

    private static void verifyShowResult(final ShowResult expected, final ShowResult actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getScore(), actual.getScore(), 0.01D),
                () -> verifyShow(expected.getShow(), actual.getShow()));
    }

    public static void verifyShowList(final List<Show> expected, final List<Show> actual) {
        validateListOf(expected, actual, ShowTestDataValidator::verifyShow);
    }

    public static void verifyShow(final Show expected, final Show actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> validateResource(expected, actual),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getType(), actual.getType()),
                () -> assertEquals(expected.getLanguage(), actual.getLanguage()),
                () -> assertEquals(expected.getGenres(), expected.getGenres()),
                () -> assertEquals(expected.getStatus(), actual.getStatus()),
                () -> assertEquals(expected.getRuntime(), actual.getRuntime()),
                () -> assertEquals(expected.getAverageRuntime(), actual.getAverageRuntime()),
                () -> assertEquals(expected.getPremiered(), actual.getPremiered()),
                () -> assertEquals(expected.getEnded(), actual.getEnded()),
                () -> assertEquals(expected.getOfficialSite(), actual.getOfficialSite()),
                () -> assertEquals(expected.getSchedule(), actual.getSchedule()),
                () -> assertEquals(expected.getRating(), actual.getRating()),
                () -> assertEquals(expected.getWeight(), actual.getWeight()),
                () -> assertEquals(expected.getNetwork(), actual.getNetwork()),
                () -> assertEquals(expected.getWebChannel(), actual.getWebChannel()),
                () -> assertEquals(expected.getDvdCountry(), actual.getDvdCountry()),
                () -> assertEquals(expected.getExternals(), actual.getExternals()),
                () -> assertEquals(expected.getImage(), actual.getImage()),
                () -> assertEquals(expected.getSummary(), actual.getSummary()),
                () -> assertEquals(expected.getUpdated(), actual.getUpdated()),
                () -> verifyListOfEpisodes(expected.getEpisodes(), actual.getEpisodes()),
                () -> verifyEpisode(expected.getNextEpisode(), actual.getNextEpisode()),
                () -> verifyEpisode(expected.getPreviousEpisode(), actual.getPreviousEpisode()),
                () -> verifyCastMembers(expected.getCast(), actual.getCast()));
    }
}
