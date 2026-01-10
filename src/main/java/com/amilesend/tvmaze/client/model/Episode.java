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

import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.ImageUrl;
import com.amilesend.tvmaze.client.model.type.Rating;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

/** Describes a television show episode. */
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
public class Episode extends EpisodeBase<Episode, ResourceLink> {
    /** The episode rating. */
    private final Rating rating;
    /** The associated episode image URLs. */
    private final ImageUrl image;
    /** The episode summary. */
    private final String summary;
    /** Any embedded resources associated with the episode. Note: can be {@code null}. */
    @SerializedName("_embedded")
    private final EmbeddedResource embeddedResource;

    /**
     * Gets the embedded list of cast members associated with the episode. Note: can be {@code null}.
     *
     * @return the list of cast members
     * @see CastMember
     */
    public List<CastMember> getGuestCast() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getGuestCast)
                .orElse(null);
    }

    /**
     * Gets the embedded show associated with the episode. Note: can be {@code null}.
     *
     * @return the show
     * @see Show
     */
    public Show getShow() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getShow)
                .orElse(null);
    }

    /** Describes the supported resource links for an {@link Episode}. */
    @UtilityClass
    public static class ResourceLinkType {
        /** The link to the last aired episode for the show. */
        public static final String SHOW = "show";
    }

    /** Encapsulates the possible embedded resources for the {@link Episode}. */
    @Builder
    @Data
    public static class EmbeddedResource {
        @SerializedName("guestcast")
        private final List<CastMember> guestCast;
        private final Show show;
    }

    /** Describes the type of additional embedded information to include in the {@link Episode}. */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class EmbeddedType implements EmbeddedQueryParameter {
        /** Include the list of guest cast members. */
        public static final EmbeddedType GUEST_CAST = new EmbeddedType("guestcast");
        /** Include the show. */
        public static final EmbeddedType SHOW = new EmbeddedType("show");

        private final String queryParameterValue;
    }
}
