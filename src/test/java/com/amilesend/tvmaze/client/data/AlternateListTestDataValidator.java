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

import com.amilesend.tvmaze.client.model.AlternateEpisode;
import com.amilesend.tvmaze.client.model.AlternateList;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;

import static com.amilesend.tvmaze.client.data.EpisodeTestDataValidator.verifyListOfEpisodes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;

@UtilityClass
public class AlternateListTestDataValidator {
    public static void verifyListOfAlternateList(
            final List<AlternateList> expected,
            final List<AlternateList> actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()));

        for (int i = 0; i < expected.size(); ++i) {
            verifyAlternateList(expected.get(i), actual.get(i));
        }
    }

    public static void verifyAlternateList(final AlternateList expected, final AlternateList actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.isDvdRelease(), actual.isDvdRelease()),
                () -> assertEquals(expected.isVerbatimOrder(), actual.isVerbatimOrder()),
                () -> assertEquals(expected.isCountryPremiere(), actual.isCountryPremiere()),
                () -> assertEquals(expected.isBroadcastPremiere(), actual.isBroadcastPremiere()),
                () -> assertEquals(expected.isLanguagePremiere(), actual.isLanguagePremiere()),
                () -> assertEquals(expected.getLanguage(), actual.getLanguage()),
                () -> assertEquals(expected.getNetwork(), actual.getNetwork()),
                () -> assertEquals(expected.getWebChannel(), actual.getWebChannel()),
                () -> verifyListOfAlternateEpisode(expected.getAlternateEpisodes(), actual.getAlternateEpisodes()),
                () -> verifyListOfEpisodes(expected.getEpisodes(), actual.getEpisodes()));
    }

    public static void verifyListOfAlternateEpisode(
            final List<AlternateEpisode> expected,
            final List<AlternateEpisode> actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); ++i) {
            verifyAlternateEpisode(expected.get(i), actual.get(i));
        }
    }

    private static void verifyAlternateEpisode(final AlternateEpisode expected, final AlternateEpisode actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getSeason(), actual.getSeason()),
                () -> assertEquals(expected.getNumber(), actual.getNumber()),
                () -> assertEquals(expected.getType(), actual.getType()),
                () -> assertEquals(expected.getAirdate(), actual.getAirdate()),
                () -> assertEquals(expected.getAirtime(), actual.getAirtime()),
                () -> assertEquals(expected.getAirstamp(), actual.getAirstamp()),
                () -> assertEquals(expected.getRuntime(), actual.getRuntime()),
                () -> verifyListOfEpisodes(expected.getEpisodes(), actual.getEpisodes()));
    }
}
