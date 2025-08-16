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
package com.amilesend.tvmaze.client.api;

import com.amilesend.client.connection.Connection;
import com.amilesend.tvmaze.client.model.EmbeddedQueryParameter;
import com.amilesend.tvmaze.client.parse.GsonFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Objects;

/**
 * The API Base class used to simplify the construction of API URLs for the {@link Connection}.
 * @see Connection
 */
@RequiredArgsConstructor
public abstract class ApiBase {
    private static final int MAX_ID_LENGTH = 32;

    /** The connection that wraps the underlying HTTP client. */
    @NonNull
    protected final Connection<GsonFactory> connection;

    /**
     * Validates input parameters and constructs a new {@link HttpUrl} used for fetching resource-index-based requests.
     *
     * @param apiPath the API URL path
     * @param pageNum the page number
     * @return the URL
     * @see HttpUrl
     */
    protected HttpUrl validateAndFormatIndexUrl(final String apiPath, final int pageNum) {
        final String formattedPageNum = validateId(pageNum);
        return HttpUrl.parse(connection.getBaseUrl() + apiPath)
                .newBuilder()
                .addQueryParameter("page", formattedPageNum)
                .build();
    }

    /**
     * Validates input parameters and constructs a new {@link HttpUrl} used for fetching resources.
     *
     * @param apiPath the primary API path
     * @param id the resource identifier
     * @param subApiPath the sub-API path that is specific to the resource
     * @param includedEmbeddedTypes the optional array of included embedded types to specify in the request URL
     * @return the URL
     * @see HttpUrl
     */
    protected HttpUrl validateAndFormatUrl(
            final String apiPath,
            final int id,
            final String subApiPath,
            final EmbeddedQueryParameter... includedEmbeddedTypes) {
        Validate.notBlank(apiPath, "apiPath must not be blank");

        return formatEmbeddedTypes(
                HttpUrl.parse(
                        connection.getBaseUrl() + apiPath + validateId(id) + subApiPath).newBuilder(),
                        includedEmbeddedTypes)
                .build();
    }

    /**
     * Used to parse and included {@link EmbeddedQueryParameter}s with the request URL as query parameters.
     *
     * @param urlBuilder the URL builder
     * @param includedEmbeddedTypes the array of included embedded types
     * @return the URL
     * @see HttpUrl
     * @see EmbeddedQueryParameter
     */
    protected static HttpUrl.Builder formatEmbeddedTypes(
            final HttpUrl.Builder urlBuilder,
            final EmbeddedQueryParameter... includedEmbeddedTypes) {
        if (ArrayUtils.isEmpty(includedEmbeddedTypes)) {
            return urlBuilder;
        }

        if (includedEmbeddedTypes.length == 1 && Objects.nonNull(includedEmbeddedTypes[0])) {
            urlBuilder.addQueryParameter("embed", includedEmbeddedTypes[0].getQueryParameterValue());
        } else {
            Arrays.stream(includedEmbeddedTypes)
                    .filter(Objects::nonNull)
                    .forEach(t -> urlBuilder.addQueryParameter("embed[]", t.getQueryParameterValue()));
        }

        return urlBuilder;
    }

    /**
     * Validates and URL encodes the given identifier value.
     *
     * @param id the identifier
     * @return the formatted identifier
     */
    protected static String validateId(final int id) {
        Validate.isTrue(id >= 0, "id must be >= 0");

        return validateId(String.valueOf(id));
    }

    /**
     * Validates and URL encodes the given identifier value.
     *
     * @param id the identifier
     * @return the formatted identifier
     */
    protected static String validateId(final String id) {
        Validate.notBlank(id, "id must not be blank");
        Validate.isTrue(id.length() <= MAX_ID_LENGTH,
                "id length must be <= " + MAX_ID_LENGTH);

        return id;
    }
}
