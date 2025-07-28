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

import com.amilesend.tvmaze.client.model.Episode;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;

import static com.amilesend.tvmaze.client.data.DataValidatorHelper.validateListOf;
import static com.amilesend.tvmaze.client.data.DataValidatorHelper.validateResource;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@UtilityClass
public class EpisodeTestDataValidator {
    public static void verifyListOfEpisodes(final List<Episode> expected, final List<Episode> actual) {
        validateListOf(expected, actual, EpisodeTestDataValidator::verifyEpisode);
    }

    public static void verifyEpisode(final Episode expected, final Episode actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        assertAll(
                () -> validateResource(expected, actual),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getSeason(), actual.getSeason()),
                () -> assertEquals(expected.getNumber(), actual.getNumber()),
                () -> assertEquals(expected.getAirdate(), actual.getAirdate()),
                () -> assertEquals(expected.getAirtime(), actual.getAirtime()),
                () -> assertEquals(expected.getAirstamp(), actual.getAirstamp()),
                () -> assertEquals(expected.getRuntime(), actual.getRuntime()),
                () -> assertEquals(expected.getRating(), actual.getRating()),
                () -> assertEquals(expected.getImage(), actual.getImage()),
                () -> assertEquals(expected.getSummary(), actual.getSummary()));
    }
}
