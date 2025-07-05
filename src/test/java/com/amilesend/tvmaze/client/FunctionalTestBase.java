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
package com.amilesend.tvmaze.client;

import com.amilesend.tvmaze.client.connection.Connection;
import com.amilesend.tvmaze.client.connection.http.OkHttpClientBuilder;
import com.amilesend.tvmaze.client.data.SerializedResource;
import com.amilesend.tvmaze.client.parse.GsonFactory;
import lombok.Getter;
import lombok.SneakyThrows;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import okhttp3.OkHttpClient;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class FunctionalTestBase {
    protected static final int SUCCESS_STATUS_CODE = 200;

    private MockWebServer mockWebServer = new MockWebServer();
    private OkHttpClient httpClient;
    @Getter
    private Connection connection;
    @Getter
    private TvMaze client;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClientBuilder().isForTest(true).build();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        setUpTvMaze();
    }

    @SneakyThrows
    @AfterEach
    public void cleanUp() {
        mockWebServer.close();
    }

    protected void setUpMockResponse(final int responseCode) {
        setUpMockResponse(responseCode, (SerializedResource) null);
    }

    @SneakyThrows
    protected void setUpMockResponse(final int responseCode, final SerializedResource responseBodyResource) {
        if (responseBodyResource == null) {
            mockWebServer.enqueue(new MockResponse.Builder()
                    .code(responseCode)
                    .build());
            return;
        }

        mockWebServer.enqueue(new MockResponse.Builder()
                .code(responseCode)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .body(new Buffer().write(responseBodyResource.toGzipCompressedBytes()))
                .build());
    }

    protected String getMockWebServerUrl() {
        return String.format("http://%s:%d", mockWebServer.getHostName(), mockWebServer.getPort());
    }

    private void setUpTvMaze() {
        connection = Connection.builder()
                .httpClient(httpClient)
                .gsonFactory(GsonFactory.getInstance())
                .baseUrl(getMockWebServerUrl())
                .build();
        client = new TvMaze(connection);
    }
}
