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
package com.amilesend.tvmaze.client.data;

import com.amilesend.tvmaze.client.model.Person;
import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.Character;
import com.amilesend.tvmaze.client.model.type.CrewMember;
import com.amilesend.tvmaze.client.model.type.PersonResult;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;

@UtilityClass
public class PersonTestDataValidator {

    public static void verifyCastMembers(final List<CastMember> expected, final List<CastMember> actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()));

        for (int i = 0; i < expected.size(); ++i) {
            final CastMember expectedCastMember = expected.get(i);
            final CastMember actualCastMember = actual.get(i);
            assertAll(
                    () -> verifyPerson(expectedCastMember.getPerson(), actualCastMember.getPerson()),
                    () -> verifyCharacter(expectedCastMember.getCharacter(), actualCastMember.getCharacter()));
        }
    }

    public static void verifyCrewMembers(final List<CrewMember> expected, final List<CrewMember> actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()));

        for (int i = 0; i < expected.size(); ++i) {
            final CrewMember expectedCrewMember = expected.get(i);
            final CrewMember actualCrewMember = actual.get(i);
            assertAll(
                    () -> assertEquals(expectedCrewMember.getType(), actualCrewMember.getType()),
                    () -> verifyPerson(expectedCrewMember.getPerson(), expectedCrewMember.getPerson()));
        }
    }

    public static void verifyPersons(final List<PersonResult> expected, final List<PersonResult> actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()));

        for (int i = 0; i < expected.size(); ++i) {
            verifyPersonResult(expected.get(i), actual.get(i));
        }
    }

    public static void verifyPersonList(final List<Person> expected, final List<Person> actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()));

        for (int i = 0; i < expected.size(); ++i) {
            verifyPerson(expected.get(i), actual.get(i));
        }
    }

    public static void verifyPerson(final Person expected, final Person actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getCountry(), actual.getCountry()),
                () -> assertEquals(expected.getBirthday(), actual.getBirthday()),
                () -> assertEquals(expected.getDeathday(), actual.getDeathday()),
                () -> assertEquals(expected.getGender(), actual.getGender()),
                () -> assertEquals(expected.getImage(), actual.getImage()),
                () -> assertEquals(expected.getUpdated(), actual.getUpdated()),
                () -> ShowTestDataValidator.verifyResourceLinks(expected.getLinks(), actual.getLinks()),
                () -> assertEquals(expected.getCastCredits(), actual.getCastCredits()));
    }

    private static void verifyPersonResult(final PersonResult expected, final PersonResult actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getScore(), actual.getScore(), 0.01D),
                () -> verifyPerson(expected.getPerson(), actual.getPerson()));
    }

    private static void verifyCharacter(final Character expected, final Character actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getImage(), actual.getImage()),
                () -> assertEquals(expected.getLinks(), actual.getLinks()));
    }
}
