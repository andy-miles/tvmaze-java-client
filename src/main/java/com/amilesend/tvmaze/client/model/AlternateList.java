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

import com.amilesend.tvmaze.client.model.type.Network;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import com.amilesend.tvmaze.client.model.type.WebChannel;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * Defines different types of episode lists for a tv show (e.g.,., differing order and/or releases in one
 * region vs. another, etc.).  For more information: <a href="https://www.tvmaze.com/faq/40/alternate-episodes">
 * https://www.tvmaze.com/faq/40/alternate-episodes</a>
 */
@SuperBuilder
@Getter
@ToString(callSuper = true)
@Slf4j
public class AlternateList extends Resource<AlternateList, ResourceLink> {
    /** The associated TVMaze website URL for this list. */
    private final String url;
    /** Flag indicator if this list represents a DVD release ordering. */
    @SerializedName("dvd_release")
    private final boolean isDvdRelease;
    /** Flag indicator if this list represents ordering that does not follow the original networks'. */
    @SerializedName("verbatim_order")
    private final boolean isVerbatimOrder;
    /** Flag indicator if this list represents the ordering of a show that aired in a different country. */
    @SerializedName("country_premiere")
    private final boolean isCountryPremiere;
    /** Flag indicator if this list represents ordering for a show that is distributed via a web/streaming channel. */
    @SerializedName("streaming_premiere")
    private final boolean isStreamingPremiere;
    /** Flag indicator if this list represents ordering for a show that is distributed via a traditional TV network. */
    @SerializedName("broadcast_premiere")
    private final boolean isBroadcastPremiere;
    /**
     * Flag indicator if this list represents ordering for a show that is aired in a different language than the \
     * original.
     */
    @SerializedName("language_premiere")
    private final boolean isLanguagePremiere;
    /** The show's language. */
    private final String language;
    /** The distribution network. */
    private final Network network;
    /** The streaming distribution network. */
    private final WebChannel webChannel;
    /** The optional embedded resources. */
    @SerializedName("_embedded")
    private final EmbeddedResource embeddedResource;

    /**
     * Gets the list of alternate episodes. Can be {@code null}.
     *
     * @return the list of alternate episodes
     * @see AlternateEpisode
     */
    public List<AlternateEpisode> getAlternateEpisodes() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getAlternateEpisodes)
                .orElse(null);
    }

    /**
     * Gets the lsit of episodes. Can be {@code null}.
     *
     * @return the list of episdoes
     * @see Episode
     */
    public List<Episode> getEpisodes() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getEpisodes)
                .orElse(null);
    }

    /** The possible embedded resource types that can be included in an {@link AlternateList}. */
    @Builder
    @Data
    public static class EmbeddedResource {
        @SerializedName("alternateepisodes")
        private final List<AlternateEpisode> alternateEpisodes;
        private final List<Episode> episodes;
    }

    /** The supported embedded resource types that can be included in an {@link AlternateList}. */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class EmbeddedType implements EmbeddedQueryParameter {
        /** Include the list of alternate episodes. */
        public static final EmbeddedType ALTERNATE_EPISODES = new EmbeddedType("alternateepisodes");
        /** Include the list of episodes. */
        public static final EmbeddedType EPISODES = new EmbeddedType("episodes");

        private final String queryParameterValue;
    }
}
