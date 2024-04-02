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
package com.amilesend.tvmaze.client.model;

import com.amilesend.tvmaze.client.model.type.ResourceLink;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Optional;

/** Describes a crew credit for a person. */
@Builder
@Data
public class CrewCredit {
    /** The credit type (i.e., job role for credit). */
    private final String type;
    /** The links associated with the credit. */
    @SerializedName("_links")
    private final Map<String, ResourceLink> links;
    /** Any embedded resources associated with the {@link CrewCredit}. Note: can be {@code null}. */
    @SerializedName("_embedded")
    private final CrewCredit.EmbeddedResource embeddedResource;

    /**
     * Gets the show associated with this credit. Note: Can be {@code null}.
     *
     * @return the show
     * @see Show
     */
    public Show getShow() {
        return Optional.ofNullable(embeddedResource)
                .map(CrewCredit.EmbeddedResource::getShow)
                .orElse(null);
    }

    /** Describes the supported resource links for a {@link CrewCredit}. */
    @UtilityClass
    public static class ResourceLinkType {
        /** Link to the show resource. */
        public static final String SHOW = "show";
    }

    /** Encapsulates the possible embedded resources for the {@link CrewCredit}. */
    @Builder
    @Data
    public static class EmbeddedResource {
        private final Show show;
    }

    /** Describes the type of additional embedded information to include in the {@link CrewCredit}. */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class EmbeddedType implements EmbeddedQueryParameter {
        /** Include the show. */
        public static final EmbeddedType SHOW = new EmbeddedType("show");

        private final String queryParameterValue;
    }
}
