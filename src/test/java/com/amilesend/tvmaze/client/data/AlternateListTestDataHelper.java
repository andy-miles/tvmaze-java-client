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
package com.amilesend.tvmaze.client.data;

import com.amilesend.tvmaze.client.model.AlternateEpisode;
import com.amilesend.tvmaze.client.model.AlternateList;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.amilesend.tvmaze.client.data.EpisodeTestDataHelper.newListOfEpisodes;

@UtilityClass
public class AlternateListTestDataHelper {
    public static List<AlternateList> newListOfAlternateList() {
        final List<AlternateList> list = new ArrayList<>(3);
        for (int i = 1; i <=3; ++i) {
            list.add(newAlternateList(i, (AlternateList.EmbeddedType) null));
        }

        return list;
    }

    public static AlternateList newAlternateList(final int id, final AlternateList.EmbeddedType... embeddedTypes) {
        return AlternateList.builder()
                .id(id)
                .url("http://www.someurl.com")
                .isDvdRelease(true)
                .isVerbatimOrder(false)
                .isCountryPremiere(false)
                .isStreamingPremiere(false)
                .isBroadcastPremiere(false)
                .isLanguagePremiere(false)
                .language(Locale.US.getLanguage())
                .network(ShowTestDataHelper.newNetwork())
                .webChannel(ShowTestDataHelper.newWebchannel())
                .embeddedResource(newAlternateListEmbeddedResources(embeddedTypes))
                .build();
    }

    public static List<AlternateEpisode> newAlternateEpisodeList(final boolean isEmbeddedEpisodesIncluded) {
        final List<AlternateEpisode> list = new ArrayList<>(3);
        for (int i = 1; i <= 3; ++i) {
            list.add(newAlternateEpisode(i, 1, i, isEmbeddedEpisodesIncluded));
        }

        return list;
    }

    private static AlternateEpisode newAlternateEpisode(
            final int id,
            final int seasonNum,
            final int episodeNum,
            final boolean isEmbeddedEpisodesIncluded) {
        final AlternateEpisode.EmbeddedResource resource = isEmbeddedEpisodesIncluded
                ? AlternateEpisode.EmbeddedResource.builder()
                    .episodes(newListOfEpisodes())
                    .build()
                : null;

        return AlternateEpisode.builder()
                .id(id)
                .url("https://www.alternateepisode.com")
                .name("Alternate Episode " + id)
                .season(seasonNum)
                .number(episodeNum)
                .type("Type")
                .airdate(LocalDate.of(2000, 12, 1))
                .airtime(LocalTime.of(14, 00))
                .airstamp(LocalDateTime.of(2000, 12, 1, 14, 00))
                .runtime(30)
                .embeddedResource(resource)
                .build();
    }

    private static AlternateList.EmbeddedResource newAlternateListEmbeddedResources(
            final AlternateList.EmbeddedType... embeddedTypes) {
        if (Objects.isNull(embeddedTypes)) {
            return null;
        }

        final AlternateList.EmbeddedResource.EmbeddedResourceBuilder builder = AlternateList.EmbeddedResource.builder();
        for (int i = 0; i < embeddedTypes.length; ++i) {
            if (Objects.isNull(embeddedTypes[i])) {
                continue;
            }

            if(embeddedTypes[i] == AlternateList.EmbeddedType.ALTERNATE_EPISODES) {
                builder.alternateEpisodes(newAlternateEpisodeList(false));
            } else if (embeddedTypes[i] == AlternateList.EmbeddedType.EPISODES) {
                builder.episodes(newListOfEpisodes());
            } else {
                throw new IllegalStateException("Unrecognized embedded resource type: " + embeddedTypes[i]);
            }
        }
        return builder.build();
    }
}
