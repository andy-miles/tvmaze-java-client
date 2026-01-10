/*
 * tvmaze-java-client - A client to access the TVMaze API
 * Copyright © 2024-2026 Andy Miles (andy.miles@amilesend.com)
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
import com.amilesend.tvmaze.client.data.ShowTestDataValidator;
import com.amilesend.tvmaze.client.model.CastCredit;
import com.amilesend.tvmaze.client.model.CrewCredit;
import com.amilesend.tvmaze.client.model.Person;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.amilesend.tvmaze.client.data.EpisodeTestDataValidator.verifyListOfEpisodes;
import static com.amilesend.tvmaze.client.data.PersonTestDataValidator.verifyPerson;
import static com.amilesend.tvmaze.client.data.PersonTestDataValidator.verifyPersonList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeopleApiFunctionalTest extends FunctionalTestBase {
    //////////////
    // getPerson
    //////////////

    @Test
    public void getPerson_withValidIdAndCastCreditsIncluded_shouldReturnPerson() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.PERSON_EMBEDDED_CAST_CREDITS);
        final Person expected = PersonTestDataHelper.newPerson(1, Person.EmbeddedType.CAST_CREDITS);

        final Person actual = getClient().getPeopleApi().getPerson(1, true);

        verifyPerson(expected, actual);
    }

    ///////////////////
    // getCastCredits
    ///////////////////

    @Test
    public void getCastCredits_withValidIdAndShowIncluded_shouldReturnListOfCastCredit() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CAST_CREDIT_LIST_EMBEDDED_SHOW);
        final List<CastCredit> expected = PersonTestDataHelper.newCastCredits(CastCredit.EmbeddedType.SHOW);

        final List<CastCredit> actual = getClient().getPeopleApi().getCastCredits(1, true);

        assertAll(
                () -> assertEquals(expected, actual),
                () -> ShowTestDataValidator.verifyShowList(
                        expected.stream().map(CastCredit::getShow).collect(Collectors.toList()),
                        actual.stream().map(CastCredit::getShow).collect(Collectors.toList())));
    }

    ///////////////////
    // getCrewCredits
    ///////////////////

    @Test
    public void getCrewCredits_withValidIdeAndShowIncluded_shouldReturnListOfCredCredit() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CREW_CREDIT_LIST_EMBEDDED_SHOW);
        final List<CrewCredit> expected = PersonTestDataHelper.newCrewCredits(CrewCredit.EmbeddedType.SHOW);

        final List<CrewCredit> actual = getClient().getPeopleApi().getCrewCredits(1, true);

        assertAll(
                () -> assertEquals(expected, actual),
                () -> ShowTestDataValidator.verifyShowList(
                        expected.stream().map(CrewCredit::getShow).collect(Collectors.toList()),
                        actual.stream().map(CrewCredit::getShow).collect(Collectors.toList())));
    }

    ////////////////////////
    // getGuestCastCredits
    ////////////////////////

    @Test
    public void getGuestCastCredits_withValidIdAndEmbeddedEpisode_shouldReturnListOfCastCredit() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.CAST_CREDIT_LIST_EMBEDDED_EPISODE);
        final List<CastCredit> expected = PersonTestDataHelper.newCastCredits(CastCredit.EmbeddedType.EPISODE);

        final List<CastCredit> actual = getClient().getPeopleApi().getGuestCastCredits(1, true);

        assertAll(
                () -> assertEquals(expected, actual),
                () -> verifyListOfEpisodes(
                        expected.stream().map(CastCredit::getEpisode).collect(Collectors.toList()),
                        actual.stream().map(CastCredit::getEpisode).collect(Collectors.toList())));
    }

    /////////////
    // getIndex
    /////////////

    @Test
    public void getIndex_withValidPageNum_shouldReturnListOfPerson() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.PERSON_LIST);
        final List<Person> expected = PersonTestDataHelper.newPersonList();

        final List<Person> actual = getClient().getPeopleApi().getIndex(0);

        verifyPersonList(expected, actual);
    }
}
