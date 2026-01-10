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
package com.amilesend.tvmaze.client.model;

import com.amilesend.tvmaze.client.model.type.Country;
import com.amilesend.tvmaze.client.model.type.ImageUrl;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Describes a person associated with a series/show/episode (e.g., cast or crew member). */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class Person extends Resource<Person, ResourceLink> {
    /** The URL associated with this person. */
    private final String url;
    /** The person's name. */
    private final String name;
    /** The person's country. */
    private final Country country;
    /** The person's birthday. */
    private final LocalDate birthday;
    /** The person's death date. */
    private final LocalDate deathday;
    /** The person's gender. */
    private final String gender;
    /** The URLs for images associated with the person. */
    private final ImageUrl image;
    /** Last updated timestamp. */
    private final long updated;
    /** Any embedded resources associated with the person. Note: can be {@code null}. */
    @SerializedName("_embedded")
    private final Person.EmbeddedResource embeddedResource;


    /**
     * Gets the embedded cast credits associated with the person. Note: Can be {@code null}.
     *
     * @return the list of cast credits
     * @see CastCredit
     */
    public List<CastCredit> getCastCredits() {
        return Optional.ofNullable(embeddedResource)
                .map(Person.EmbeddedResource::getCastCredits)
                .orElse(null);
    }

    /** Encapsulates the possible embedded resources for the {@link Show}. */
    @Builder
    @Data
    public static class EmbeddedResource {
        @SerializedName("castcredits")
        private final List<CastCredit> castCredits;
    }

    /** Describes the type of additional embedded information to include in the {@link Show}. */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class EmbeddedType implements EmbeddedQueryParameter {
        /** Include a list of cast credits. */
        public static final EmbeddedType CAST_CREDITS = new EmbeddedType("castcredits");

        private final String queryParameterValue;
    }
}
