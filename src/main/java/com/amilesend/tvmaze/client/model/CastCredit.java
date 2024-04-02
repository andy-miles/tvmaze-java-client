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

/** Describes a cast credit for a person. */
@Builder
@Data
public class CastCredit {
    /** Flag indicator if the person plays themself. */
    @SerializedName("self")
    private final boolean isSelf;
    /** Flag indicator if the person performed with their voice only (e.g., narration or a animation character). */
    @SerializedName("voice")
    private final boolean isVoice;
    /** The links associated with the credit. */
    @SerializedName("_links")
    private final Map<String, ResourceLink> links;
    /** Any embedded resources associated with the {@link CastCredit}. Note: can be {@code null}. */
    @SerializedName("_embedded")
    private final CastCredit.EmbeddedResource embeddedResource;

    /**
     * Gets the show associated with the cast credit. Note: Can be {@code null}.
     *
     * @return the show
     * @see Show
     */
    public Show getShow() {
        return Optional.ofNullable(embeddedResource)
                .map(CastCredit.EmbeddedResource::getShow)
                .orElse(null);
    }

    /**
     * Gets the episode associated with the cast credit. Note: Can be {@code null}.
     * @return
     */
    public Episode getEpisode() {
        return Optional.ofNullable(embeddedResource)
                .map(CastCredit.EmbeddedResource::getEpisode)
                .orElse(null);
    }

    /** Describes the supported resource links for a {@link CastCredit}. */
    @UtilityClass
    public static class ResourceLinkType {
        /** Link to the show resource. */
        public static final String SHOW = "show";
        /** Link to the episode resource. */
        public static final String EPISODE = "episode";
        /** Link to the character resource. */
        public static final String CHARACTER = "character";
    }

    /** Encapsulates the possible embedded resources for the {@link CastCredit}. */
    @Builder
    @Data
    public static class EmbeddedResource {
        private final Show show;
        private final Episode episode;
    }

    /** Describes the type of additional embedded information to include in the {@link CastCredit}. */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class EmbeddedType implements EmbeddedQueryParameter {
        /** Include the associated show. */
        public static final EmbeddedType SHOW = new EmbeddedType("show");
        /** Include the associated episode. */
        public static final EmbeddedType EPISODE = new EmbeddedType("episode");

        private final String queryParameterValue;
    }
}
