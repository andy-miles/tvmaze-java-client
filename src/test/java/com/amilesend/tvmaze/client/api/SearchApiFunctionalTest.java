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
import com.amilesend.tvmaze.client.data.ShowTestDataHelper;
import com.amilesend.tvmaze.client.data.ShowTestDataValidator;
import com.amilesend.tvmaze.client.model.Show;
import com.amilesend.tvmaze.client.model.type.PersonResult;
import com.amilesend.tvmaze.client.model.type.ShowResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.amilesend.tvmaze.client.data.PersonTestDataValidator.verifyPersons;

public class SearchApiFunctionalTest extends FunctionalTestBase {
    ////////////////
    // searchShows
    ////////////////

    @Test
    public void searchShows_withValidRequest_shouldReturnListOfShows() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.RESULT_LIST);
        final List<ShowResult> expected = ShowTestDataHelper.newShowResultList();

        final List<ShowResult> actual = getClient().getSearchApi().searchShows("show");

        ShowTestDataValidator.verifyShowResultList(expected, actual);
    }

    @Test
    public void singleSearchShow_withValidRequest_shouldReturnShow() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.SHOW);
        final Show expected = ShowTestDataHelper.newShow();

        final Show actual = getClient().getSearchApi().singleSearchShow("show", (Show.EmbeddedType) null);

        ShowTestDataValidator.verifyShow(expected, actual);
    }

    /////////////////////
    // singleSearchShow
    /////////////////////

    @Test
    public void singleSearchShow_withEpisodesIncluded_shouldReturnShow() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.EMBEDDED_EPISODES);
        final Show expected = ShowTestDataHelper.newShow(1, Show.EmbeddedType.EPISODES);

        final Show actual = getClient().getSearchApi().singleSearchShow("show", Show.EmbeddedType.EPISODES);

        ShowTestDataValidator.verifyShow(expected, actual);
    }

    /////////////////
    // searchPeople
    /////////////////

    @Test
    public void searchPeople_withValidRequest_shouldReturnPersons() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.People.PERSON_RESULT_LIST);
        final List<PersonResult> expected = PersonTestDataHelper.newPersonResults();

        final List<PersonResult> actual = getClient().getSearchApi().searchPeople("person");

        verifyPersons(expected, actual);
    }

    ///////////////
    // lookupShow
    ///////////////

    @Test
    public void lookupShow_withValidTypeAndId_shouldReturnShow() {
        setUpMockResponse(SUCCESS_STATUS_CODE, SerializedResource.Show.SHOW);
        final Show expected = ShowTestDataHelper.newShow();

        final Show actual = getClient().getSearchApi().lookupShow(SearchApi.ShowLookupIdType.IMDB, "ttIdValue");

        ShowTestDataValidator.verifyShow(expected, actual);
    }
}
