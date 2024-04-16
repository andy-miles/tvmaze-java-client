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
import com.amilesend.tvmaze.client.model.Episode;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.amilesend.tvmaze.client.data.EpisodeTestDataHelper.newListOfEpisodes;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataValidator.verifyListOfEpisodes;

public class ScheduleApiFunctionalTest extends FunctionalTestBase {
    ////////////////
    // getSchedule
    ////////////////

    @Test
    public void getSchedule_withCountryCodeAndDate_shouldReturnListOfEpisodes() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST);
        final List<Episode> expected = newListOfEpisodes(Episode.EmbeddedType.SHOW);

        final List<Episode> actual = getClient()
                .getScheduleApi()
                .getSchedule("US", LocalDate.of(2024, 01, 15));

        verifyListOfEpisodes(expected, actual);
    }

    ////////////////////////////
    // getWebStreamingSchedule
    ////////////////////////////

    @Test
    public void getWebStreamingSchedule_withCountryCodeAndDate_shouldReturnListOfEpisodes() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST);
        final List<Episode> expected = newListOfEpisodes(Episode.EmbeddedType.SHOW);

        final List<Episode> actual = getClient()
                .getScheduleApi()
                .getWebStreamingSchedule("US", LocalDate.of(2024, 01, 15));

        verifyListOfEpisodes(expected, actual);
    }

    ////////////////////
    // getFullSchedule
    ////////////////////

    @Test
    public void getFullScheduleSchedule_shouldReturnListOfEpisodes() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.LIST);
        final List<Episode> expected = newListOfEpisodes(Episode.EmbeddedType.SHOW);

        final List<Episode> actual = getClient().getScheduleApi().getFullSchedule();

        verifyListOfEpisodes(expected, actual);
    }
}
