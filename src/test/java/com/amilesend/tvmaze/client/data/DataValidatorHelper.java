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

import com.amilesend.tvmaze.client.model.Resource;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@UtilityClass
public class DataValidatorHelper {
    public static void validateResource(final Resource expected, final Resource actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> verifyLinks(expected.getLinks(), actual.getLinks()));
    }

    public static <T> void verifyLinks(
            final Map<String, T> expected,
            final Map<String, T> actual) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        expected.forEach((k, expectedValue) -> {
            final T actualValue = actual.get(k);
            if (Objects.isNull(expectedValue)) {
                assertNull(actualValue);
            } else {
                assertEquals(expectedValue, actualValue);
            }
        });
    }

    public static <T> void validateListOf(
            final List<T> expected,
            final List<T> actual,
            final ItemValidator<T> itemValidator) {
        if (Objects.isNull(expected)) {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        if (expected.isEmpty()) {
            assertTrue(actual.isEmpty());
            return;
        }

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); ++i) {
            itemValidator.validate(expected.get(i), actual.get(i));
        }
    }
}
