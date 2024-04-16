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

import com.amilesend.tvmaze.client.parse.GsonFactory;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConnectionTestBase {
    protected static final String REQUEST_URL = "http://localhost";
    protected static final int SUCCESS_RESPONSE_CODE = 200;
    protected static final int REMOTE_SUCCESS_RESPONSE_CODE = 202;
    protected static final int REQUEST_ERROR_CODE = 403;
    protected static final int THROTTLED_ERROR_CODE = 429;
    protected static final int SERVER_ERROR_RESPONSE_CODE = 503;
    protected static final long BYTES_TRANSFERRED = 1024L;

    @Mock
    protected OkHttpClient mockHttpClient;
    @Mock
    protected Gson mockGson;
    @Mock
    protected GsonFactory mockGsonFactory;
    protected Connection connectionUnderTest;

    @BeforeEach
    public void setUp() {
        when(mockGsonFactory.newInstanceForConnection()).thenReturn(mockGson);
        connectionUnderTest =
                spy(Connection.builder()
                        .httpClient(mockHttpClient)
                        .gsonFactory(mockGsonFactory)
                        .baseUrl(REQUEST_URL)
                        .build());

    }

    protected Response newMockedResponse(final int code) {
        final ResponseBody mockBody = mock(ResponseBody.class);
        lenient().when(mockBody.byteStream()).thenReturn(mock(InputStream.class));
        lenient().when(mockBody.source()).thenReturn(mock(BufferedSource.class));

        final Response mockResponse = mock(Response.class);
        lenient().when(mockResponse.code()).thenReturn(code);
        lenient().when(mockResponse.isSuccessful()).thenReturn(code == SUCCESS_RESPONSE_CODE);
        lenient().when(mockResponse.body()).thenReturn(mockBody);

        return mockResponse;
    }

    protected Response newMockedResponse(final int code, final Long retryAfterSeconds) {
        final Response mockResponse = mock(Response.class);
        when(mockResponse.code()).thenReturn(code);
        lenient().when(mockResponse.isSuccessful()).thenReturn(String.valueOf(code).startsWith("2"));
        if (retryAfterSeconds != null) {
            lenient().when(mockResponse.header(eq("Retry-After"))).thenReturn(String.valueOf(retryAfterSeconds));
        }

        return mockResponse;
    }

    @SneakyThrows
    protected Response setUpHttpClientMock(final Response mockResponse) {
        final Call mockCall = mock(Call.class);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

        return mockResponse;
    }
}
