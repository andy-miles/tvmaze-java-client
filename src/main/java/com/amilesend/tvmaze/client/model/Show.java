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

import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.Country;
import com.amilesend.tvmaze.client.model.type.ImageUrl;
import com.amilesend.tvmaze.client.model.type.Network;
import com.amilesend.tvmaze.client.model.type.Rating;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import com.amilesend.tvmaze.client.model.type.Schedule;
import com.amilesend.tvmaze.client.model.type.WebChannel;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/** Describes a television show. */
@SuperBuilder
@Getter
@ToString(callSuper = true)
@Slf4j
public class Show extends Resource<Show, ResourceLink> {
    /** The URL for the show. */
    private final String url;
    /** The name of the show. */
    private final String name;
    /** The type of show. */
    private final String type;
    /** The primary language of the show. */
    private final String language;
    /** The list of associated genres for the show. */
    private final List<String> genres;
    /** The airing state. */
    private final String status;
    /** The runtime in minutes. */
    private final int runtime;
    /** The average runtime in minutes. */
    private final int averageRuntime;
    /** The date the series was initially aired. */
    private final LocalDate premiered;
    /** The date when the series was ended, if applicable. */
    private final LocalDate ended;
    /** The URL for the show's official site. */
    private final String officialSite;
    /** The airing schedule. */
    private final Schedule schedule;
    /** The show rating. */
    private final Rating rating;
    /** The weight associated with the shows search relevance and/or popularity. */
    private final int weight;
    /** The associated network that air/distributes the show. */
    private final Network network;
    /** The streaming channel that the show is aired on. */
    private final WebChannel webChannel;
    /** The associated country. */
    private final Country dvdCountry;
    /**
     * A map of source providers and associated identifiers. The key is the external source id (e.g., tvrage,
     * thetvdb, imdb, etc.), and the value is the identifier.
     */
    private final Map<String, Object> externals;
    /** The images associated with the show. */
    private final ImageUrl image;
    /** A brief show summary. */
    private final String summary;
    /** Indicates when this show was last updated. */
    private final long updated;
    /** Any embedded resources associated with the show. Note: can be {@code null}. */
    @SerializedName("_embedded")
    private final EmbeddedResource embeddedResource;

    /**
     * Gets the embedded episodes associated with the show. Note: Can be {@code null}.
     *
     * @return the list of episodes
     * @see Episode
     */
    public List<Episode> getEpisodes() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getEpisodes)
                .orElse(null);
    }

    /**
     * Gets the embedded lit of cast members. Note: Can be {@code null}.
     *
     * @return the list of cast members
     * @see CastMember
     */
    public List<CastMember> getCast() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getCast)
                .orElse(null);
    }

    /**
     * Gets the next airing episode. Note: Can be {@code null}.
     *
     * @return the next episode
     * @see Episode
     */
    public Episode getNextEpisode() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getNextEpisode)
                .orElse(null);
    }

    /**
     * The previous aired episode. Note: Can be {@code null}.
     *
     * @return the previous episode
     * @see Episode
     */
    public Episode getPreviousEpisode() {
        return Optional.ofNullable(embeddedResource)
                .map(EmbeddedResource::getPreviousEpisode)
                .orElse(null);
    }

    /**
     * Gets the map of external show identifiers. he key is the external source id (e.g., tvrage,
     * thetvdb, imdb, etc.), and the value is the identifier.
     *
     * @return the map of external identifiers
     */
    public Map<String, String> getExternals() {
        if (Objects.isNull(externals) || externals.isEmpty()) {
            return null;
        }

        return externals.entrySet().stream()
                .filter(e -> Objects.nonNull(e.getValue()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> convertExternalIdToString(e.getValue())));
    }

    private static String convertExternalIdToString(final Object val) {
        if (String.class.isInstance(val)) {
            return (String) val;
        }

        if (Double.class.isInstance(val)) {
            final Double doubleVal = (Double) val;
            final int castedVal = doubleVal.intValue();
            return String.valueOf(castedVal);
        }

        if (Integer.class.isInstance(val)) {
            final Integer integerVal = (Integer) val;
            return String.valueOf(integerVal);
        }

        log.warn("Unsupported external Id [{}] of type [{}]", val, val.getClass());
        return null;
    }

    /** Describes the supported resource links for a {@link Show}. */
    @UtilityClass
    public static class ResourceLinkType {
        /** The link to the last aired episode for the show. */
        public static final String PREVIOUS_EPISODE = "previousepisode";
    }

    /** Encapsulates the possible embedded resources for the {@link Show}. */
    @Builder
    @Data
    public static class EmbeddedResource {
        private final List<CastMember> cast;
        private final List<Episode> episodes;
        @SerializedName("previousepisode")
        private final Episode previousEpisode;
        @SerializedName("nextepisode")
        private final Episode nextEpisode;
    }

    /** Describes the type of additional embedded information to include in the {@link Show}. */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class EmbeddedType implements EmbeddedQueryParameter {
        /** Include a list of cast members. */
        public static final EmbeddedType CAST = new EmbeddedType("cast");
        /** Include a list of episodes. */
        public static final EmbeddedType EPISODES = new EmbeddedType("episodes");
        /** Include the next airing episode, if available. */
        public static final EmbeddedType NEXT_EPISODE = new EmbeddedType("nextepisode");
        /** Include the previous airing episode. */
        public static final EmbeddedType PREVIOUS_EPISODE = new EmbeddedType("previousepisode");

        private final String queryParameterValue;
    }
}
