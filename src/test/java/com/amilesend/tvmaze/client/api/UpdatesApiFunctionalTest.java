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

import com.amilesend.tvmaze.client.FunctionalTestBase;
import com.amilesend.tvmaze.client.data.SerializedResource;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdatesApiFunctionalTest extends FunctionalTestBase {

    ///////////////////
    // getShowUpdates
    ///////////////////

    @Test
    public void getShowUpdates_sinceThePastDay_shouldReturnUpdates() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.UPDATES);
        final Map<Integer, Long> expected = newUpdatesTestData();

        final Map<Integer, Long> actual = getClient().getUpdatesApi().getShowUpdates(UpdatesApi.Since.DAY);

        assertEquals(expected, actual);
    }

    /////////////////////
    // getPersonUpdates
    /////////////////////

    @Test
    public void getPersonUpdates_sinceThePastMonth_shouldReturnUpdates() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.UPDATES);
        final Map<Integer, Long> expected = newUpdatesTestData();

        final Map<Integer, Long> actual = getClient().getUpdatesApi().getPersonUpdates(UpdatesApi.Since.MONTH);

        assertEquals(expected, actual);
    }

    private static Map<Integer, Long> newUpdatesTestData() {
        return Map.of(1, 100L, 2, 101L, 3, 103L);
    }
}
