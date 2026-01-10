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
package com.amilesend.tvmaze.client.data;

import com.amilesend.tvmaze.client.model.Episode;
import com.amilesend.tvmaze.client.model.Resource;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class EpisodeTestDataHelper {
    public static List<Episode> newListOfEpisodes(final Episode.EmbeddedType... embeddedTypes) {
        final List<Episode> episodes = new ArrayList<>(10);
        int idCounter = 100;
        for (int seasonNum = 1; seasonNum <= 2; ++seasonNum) {
            for (int episodeNum  = 1; episodeNum <= 5; ++episodeNum) {
                episodes.add(newEpisode(idCounter++, seasonNum, episodeNum, embeddedTypes));
            }
        }

        return episodes;
    }

    public static Episode newEpisode(
            final int id,
            final int seasonNum,
            final int episodeNum,
            final Episode.EmbeddedType... embeddedTypes) {
        final String name = "Show Episode Season " + seasonNum + " Episode " + episodeNum;
        final Map<String, ResourceLink> links = Map.of(
                Resource.SELF_RESOURCE_LINK_TYPE,
                ResourceLink.builder().href("http://self.com").build(),
                Episode.ResourceLinkType.SHOW,
                ResourceLink.builder().name("The Show").href("http://showlink.com").build());

        return Episode.builder()
                .airdate(LocalDate.of(2012, 04, 15))
                .airstamp(LocalDateTime.of(2012 , 04, 15, 22, 00))
                .airtime(LocalTime.of(22, 00))
                .embeddedResource(newEpisodeEmbeddedResources(embeddedTypes))
                .id(id)
                .image(ShowTestDataHelper.newImageUrl())
                .links(links)
                .name(name)
                .number(episodeNum)
                .rating(ShowTestDataHelper.newRating())
                .runtime(30)
                .season(seasonNum)
                .summary(name)
                .url("http://www.someurl.com")
                .build();
    }

    private static Episode.EmbeddedResource newEpisodeEmbeddedResources(final Episode.EmbeddedType... embeddedTypes) {
        if (Objects.isNull(embeddedTypes)) {
            return null;
        }

        final Episode.EmbeddedResource.EmbeddedResourceBuilder builder = Episode.EmbeddedResource.builder();
        for (int i = 0; i < embeddedTypes.length; ++i) {
            if (Objects.isNull(embeddedTypes[i])) {
                continue;
            }

            if (embeddedTypes[i] == Episode.EmbeddedType.GUEST_CAST) {
                builder.guestCast(PersonTestDataHelper.newCastMembers());
            } else if (embeddedTypes[i] == Episode.EmbeddedType.SHOW) {
                builder.show(ShowTestDataHelper.newShow());
            } else {
                throw new IllegalStateException("Unrecognized Show Embedded Resource Type: " + embeddedTypes[i]);
            }
        }
        return builder.build();
    }
}
