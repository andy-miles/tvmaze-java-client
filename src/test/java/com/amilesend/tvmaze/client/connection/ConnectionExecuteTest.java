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

import com.amilesend.tvmaze.client.model.Show;
import com.amilesend.tvmaze.client.parse.parser.GsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.Request;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConnectionExecuteTest extends ConnectionTestBase {
    ////////////////////////////////////
    // Execute (with response body)
    ////////////////////////////////////

    @Test
    public void execute_withValidRequestAndResponse_shouldReturnParsedObject() {
        final Show mockShow = mock(Show.class);
        final GsonParser<Show> mockParser = mock(GsonParser.class);
        when(mockParser.parse(any(Gson.class), any(InputStream.class))).thenReturn(mockShow);
        setUpHttpClientMock(newMockedResponse(SUCCESS_RESPONSE_CODE));

        try (final MockedConstruction<GZIPInputStream> streamCons = mockConstruction(GZIPInputStream.class)) {
            final Show actual = connectionUnderTest.execute(mock(Request.class), mockParser);

            assertAll(
                    () -> assertEquals(mockShow, actual),
                    () -> verify(mockParser).parse(isA(Gson.class), isA(InputStream.class)));
        }
    }

    @Test
    public void execute_withThrottledResponse_shouldThrowException() {
        final long expected = 60L;
        setUpHttpClientMock(newMockedResponse(THROTTLED_ERROR_CODE, expected));

        final Throwable thrown = assertThrows(ThrottledException.class,
                () -> connectionUnderTest.execute(mock(Request.class), mock(GsonParser.class)));

        assertEquals(expected, ((ThrottledException) thrown).getRetryAfterSeconds());
    }

    @Test
    public void execute_withThrottledResponseAndNullRetryAfterHeader_shouldThrowException() {
        setUpHttpClientMock(newMockedResponse(THROTTLED_ERROR_CODE, (Long) null));

        final Throwable thrown = assertThrows(ThrottledException.class,
                () -> connectionUnderTest.execute(mock(Request.class), mock(GsonParser.class)));

        assertEquals(10L, ((ThrottledException) thrown).getRetryAfterSeconds());
    }

    @Test
    public void execute_withParseException_shouldThrowException() {
        final GsonParser<Show> mockParser = mock(GsonParser.class);
        when(mockParser.parse(any(Gson.class), any(InputStream.class))).thenThrow(new JsonParseException("Exception"));
        setUpHttpClientMock(newMockedResponse(SUCCESS_RESPONSE_CODE));

        try (final MockedConstruction<GZIPInputStream> streamCons = mockConstruction(GZIPInputStream.class)) {
            final Throwable thrown = assertThrows(ResponseParseException.class,
                    () -> connectionUnderTest.execute(mock(Request.class), mockParser));
            assertInstanceOf(JsonParseException.class, thrown.getCause());
        }
    }

    @Test
    public void execute_withServerErrorResponseCode_shouldThrowException() {
        setUpHttpClientMock(newMockedResponse(SERVER_ERROR_RESPONSE_CODE));

        assertThrows(ResponseException.class,
                () -> connectionUnderTest.execute(mock(Request.class), mock(GsonParser.class)));
    }

    @Test
    public void execute_withRequestErrorResponseCode_shouldThrowException() {
        setUpHttpClientMock(newMockedResponse(REQUEST_ERROR_CODE));

        assertThrows(RequestException.class,
                () -> connectionUnderTest.execute(mock(Request.class), mock(GsonParser.class)));
    }

    @Test
    public void execute_withIOException_shouldThrowException() {
        setUpHttpClientMock(new IOException("Exception"));

        final Throwable thrown = assertThrows(RequestException.class,
                () -> connectionUnderTest.execute(mock(Request.class), mock(GsonParser.class)));

        assertInstanceOf(IOException.class, thrown.getCause());
    }

    @Test
    public void execute_withInvalidParameters_shouldThrowException() {
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> connectionUnderTest.execute(null, mock(GsonParser.class))),
                () -> assertThrows(NullPointerException.class,
                        () -> connectionUnderTest.execute(mock(Request.class), null)));
    }

    @SneakyThrows
    private void setUpHttpClientMock(final IOException ioException) {
        final Call mockCall = mock(Call.class);
        when(mockCall.execute()).thenThrow(ioException);
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);
    }
}
