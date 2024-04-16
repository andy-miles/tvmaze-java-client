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
import com.amilesend.tvmaze.client.data.PersonTestDataHelper;
import com.amilesend.tvmaze.client.data.SerializedResource;
import com.amilesend.tvmaze.client.model.Episode;
import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.CrewMember;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.amilesend.tvmaze.client.data.EpisodeTestDataHelper.newEpisode;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataValidator.verifyEpisode;
import static com.amilesend.tvmaze.client.data.PersonTestDataValidator.verifyCastMembers;
import static com.amilesend.tvmaze.client.data.PersonTestDataValidator.verifyCrewMembers;

public class EpisodesApiFunctionalTest extends FunctionalTestBase {
    ///////////////
    // getEpisode
    ///////////////

    @Test
    public void getEpisodes_withValidId_shouldReturnEpisode() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Episode.EMBEDDED_SHOW);
        final Episode expected = newEpisode(1, 1, 1, Episode.EmbeddedType.SHOW);

        final Episode actual = getClient().getEpisodesApi().getEpisode(1, true);

        verifyEpisode(expected, actual);
    }

    /////////////////
    // getGuestCast
    /////////////////

    @Test
    public void getGuestCast_withValidEpisodeId_shouldReturnListOfCastMembers() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CAST_MEMBER_LIST);
        final List<CastMember> expected = PersonTestDataHelper.newCastMembers();

        final List<CastMember> actual = getClient().getEpisodesApi().getGuestCast(1);

        verifyCastMembers(expected, actual);
    }

    /////////////////
    // getGuestCrew
    /////////////////

    @Test
    public void getGuestCrew_withValidEpisodeId_shouldReturnListOfCrewMembers() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CREW_MEMBER_LIST);
        final List<CrewMember> expected = PersonTestDataHelper.newCrewMembers();

        final List<CrewMember> actual = getClient().getEpisodesApi().getGuestCrew(1);

        verifyCrewMembers(expected, actual);
    }
}
