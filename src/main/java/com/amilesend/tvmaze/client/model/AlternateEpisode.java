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
package com.amilesend.tvmaze.client.model;

import com.amilesend.tvmaze.client.model.type.ResourceLink;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

/**
 * Describes an episode for an alternate list (e.g., DVD ordering, etc.).  For more information:
 * <a href="https://www.tvmaze.com/faq/40/alternate-episodes">https://www.tvmaze.com/faq/40/alternate-episodes</a>.
 *
 * @see AlternateList
 */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class AlternateEpisode extends EpisodeBase<AlternateEpisode, List<ResourceLink>> {
    /** The optional embedded resources. */
    @SerializedName("_embedded")
    private final EmbeddedResource embeddedResource;

    /**
     * Gets the list of embedded episodes. Note: This can be {@code null}.
     *
     * @return the list of episodes
     */
    public List<Episode> getEpisodes() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getEpisodes)
                .orElse(null);
    }

    /** The possible embedded resource types that can be included in an {@link AlternateEpisode}. */
    @Builder
    @Data
    public static class EmbeddedResource {
        private final List<Episode> episodes;
    }

    /** The supported embedded resource types that can be included in an {@link AlternateEpisode}. */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class EmbeddedType implements EmbeddedQueryParameter {
        /** Include the associated list of episodes. */
        public static final EmbeddedType EPISODES = new EmbeddedType("episodes");

        /** The query parameter value associated with the embedded resource type. */
        private final String queryParameterValue;
    }
}
