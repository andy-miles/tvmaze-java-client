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
package com.amilesend.tvmaze.client.data;

import com.amilesend.tvmaze.client.model.Person;
import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.Character;
import com.amilesend.tvmaze.client.model.type.CrewMember;
import com.amilesend.tvmaze.client.model.type.PersonResult;
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
public class PersonTestDataValidator {
    public static void verifyCastMembers(final List<CastMember> expected, final List<CastMember> actual) {
        validateListOf(expected, actual, PersonTestDataValidator::verifyCastMember);
    }

    private static void verifyCastMember(final CastMember expected, final CastMember actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> verifyPerson(expected.getPerson(), actual.getPerson()),
                () -> verifyCharacter(expected.getCharacter(), actual.getCharacter()));
    }

    public static void verifyCrewMembers(final List<CrewMember> expected, final List<CrewMember> actual) {
        validateListOf(expected, actual, PersonTestDataValidator::verifyCrewMember);
    }

    private static void verifyCrewMember(final CrewMember expected, final CrewMember actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getType(), actual.getType()),
                () -> verifyPerson(expected.getPerson(), actual.getPerson()));
    }

    public static void verifyPersons(final List<PersonResult> expected, final List<PersonResult> actual) {
        validateListOf(expected, actual, PersonTestDataValidator::verifyPersonResult);
    }

    public static void verifyPersonList(final List<Person> expected, final List<Person> actual) {
        validateListOf(expected, actual, PersonTestDataValidator::verifyPerson);
    }

    public static void verifyPerson(final Person expected, final Person actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        assertAll(
                () -> validateResource(expected, actual),
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getCountry(), actual.getCountry()),
                () -> assertEquals(expected.getBirthday(), actual.getBirthday()),
                () -> assertEquals(expected.getDeathday(), actual.getDeathday()),
                () -> assertEquals(expected.getGender(), actual.getGender()),
                () -> assertEquals(expected.getImage(), actual.getImage()),
                () -> assertEquals(expected.getUpdated(), actual.getUpdated()),
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
                () -> validateResource(expected, actual),
                () -> assertEquals(expected.getUrl(), actual.getUrl()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getImage(), actual.getImage()));
    }
}
