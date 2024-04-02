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
package com.amilesend.tvmaze.client.connection;

import okhttp3.Request;
import org.junit.jupiter.api.Test;

import static com.google.common.net.HttpHeaders.ACCEPT;
import static com.google.common.net.HttpHeaders.ACCEPT_ENCODING;
import static com.google.common.net.HttpHeaders.USER_AGENT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConnectionTest extends ConnectionTestBase {
    @Test
    public void ctor_withInvalidParameters_shouldThrowException() {
        final String baseUrl = "https://someurl.com";

        assertAll(
                () -> assertThrows(NullPointerException.class, () -> new Connection(
                        null,
                        mockGsonFactory,
                        baseUrl)),
                () -> assertThrows(NullPointerException.class, () -> new Connection(
                        mockHttpClient,
                        null,
                        baseUrl)));
    }

    @Test
    public void ctor_withBaseUrl_shouldUseConfiguredUrl() {
        final Connection connection = new Connection(
                mockHttpClient,
                mockGsonFactory,
                "http://localhost");
        assertEquals("http://localhost", connection.getBaseUrl());
    }

    @Test
    public void newRequestBuilder_shouldReturnBuilderWithHeadersDefined() {
        final Request actual = connectionUnderTest.newRequestBuilder().url(REQUEST_URL).build();

        assertAll(
                () -> assertEquals("TvMazeJavaClient/1.0", actual.header(USER_AGENT)),
                () -> assertEquals("gzip", actual.header(ACCEPT_ENCODING)),
                () -> assertEquals(Connection.JSON_CONTENT_TYPE, actual.header(ACCEPT)));
    }
}
