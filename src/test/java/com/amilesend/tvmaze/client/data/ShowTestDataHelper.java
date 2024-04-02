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
package com.amilesend.tvmaze.client.data;

import com.amilesend.tvmaze.client.model.Episode;
import com.amilesend.tvmaze.client.model.Image;
import com.amilesend.tvmaze.client.model.Resource;
import com.amilesend.tvmaze.client.model.Season;
import com.amilesend.tvmaze.client.model.Show;
import com.amilesend.tvmaze.client.model.type.Alias;
import com.amilesend.tvmaze.client.model.type.Country;
import com.amilesend.tvmaze.client.model.type.ImageResolution;
import com.amilesend.tvmaze.client.model.type.ImageResolutions;
import com.amilesend.tvmaze.client.model.type.ImageUrl;
import com.amilesend.tvmaze.client.model.type.Network;
import com.amilesend.tvmaze.client.model.type.Rating;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import com.amilesend.tvmaze.client.model.type.Schedule;
import com.amilesend.tvmaze.client.model.type.ShowResult;
import com.amilesend.tvmaze.client.model.type.WebChannel;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.amilesend.tvmaze.client.data.EpisodeTestDataHelper.newEpisode;
import static com.amilesend.tvmaze.client.data.EpisodeTestDataHelper.newListOfEpisodes;

@UtilityClass
public class ShowTestDataHelper {

    public static List<Alias> newAliasList() {
        final List<Alias> aliasList = new ArrayList<>(4);
        for (int i = 1; i<= 4; ++i) {
            aliasList.add(Alias.builder()
                    .country(newCountry())
                    .name("Alias " + i)
                    .build());
        }

        return aliasList;
    }

    public static List<Season> newListOfSeasons() {
        final List<Season> seasons = new ArrayList<>(2);
        for (int i = 1; i <= 2; ++i) {
            seasons.add(newSeason(i, i));
        }

        return seasons;
    }

    public static Season newSeason(final int id, final int seasonNum) {
        return Season.builder()
                .id(id)
                .url("https://someurl.com")
                .number(seasonNum)
                .name(id + " Season " + seasonNum)
                .episodeOrder(1)
                .premiereDate(LocalDate.of(2020, 1, 12))
                .endDate(LocalDate.of(2022, 3, 15))
                .network(newNetwork())
                .image(newImageUrl())
                .build();

    }

    public static List<Image> newImageList() {
        final List<Image> images = new ArrayList<>(5);
        for (int i = 1; i <= 5; ++i) {
            images.add(newImage(i));
        }

        return images;
    }

    public static List<ShowResult> newShowResultList() {
        final List<ShowResult> showResults = new ArrayList<>(5);
        for (int i = 1; i <= 5; ++i) {
            showResults.add(newShowResult(100D - (i * 12.2D), i));
        }

        return showResults;
    }

    public static List<Show> newShowList() {
        final List<Show> showList = new ArrayList<>(5);
        for (int i = 1; i <= 5; ++i) {
            showList.add(newShow(i));
        }

        return showList;
    }

    public static Show newShow() {
        return newShow(1);
    }

    public static Show newShow(
            final int id,
            final Show.EmbeddedType... embeddedTypes) {
        final Map<String, ResourceLink> resourceLinks = Map.of(
                Resource.SELF_RESOURCE_LINK_TYPE,
                ResourceLink.builder().href("https://selfurl.com").build(),
                Show.ResourceLinkType.PREVIOUS_EPISODE,
                ResourceLink.builder().name("Some Previous Episode").href("https://previous.com").build());
        return Show.builder()
                .averageRuntime(30)
                .dvdCountry(null)
                .embeddedResource(newShowEmbeddedResources(embeddedTypes))
                .ended(LocalDate.of(2017, 04, 16))
                .externals(newExternals())
                .genres(List.of("Drama", "Romance"))
                .id(id)
                .image(newImageUrl())
                .language("English")
                .links(resourceLinks)
                .name("Show " + id)
                .network(newNetwork())
                .officialSite("http://www.someofficialsite.com")
                .premiered(LocalDate.of(2012, 04, 15))
                .rating(newRating())
                .runtime(30)
                .schedule(newSchedule())
                .summary("The summary for Show " + id)
                .status("Ended")
                .type("Scripted")
                .updated(1704794122L)
                .url("https://www.someshowurl.com/")
                .webChannel(newWebchannel())
                .weight(98)
                .build();
    }

    static WebChannel newWebchannel() {
        return WebChannel.builder()
                .country(newCountry())
                .id(1)
                .name("WebChannelName")
                .officialSite("https://www.somechannel.com")
                .build();
    }


    static Country newCountry() {
        return Country.builder()
                .code("US")
                .name("United States")
                .timezone("America/New_York")
                .build();
    }

    static ImageUrl newImageUrl() {
        return ImageUrl.builder()
                .medium("https://static.tvmaze.com/uploads/images/medium_portrait/31/78286.jpg")
                .original("https://static.tvmaze.com/uploads/images/original_untouched/31/78286.jpg")
                .build();
    }
    static Rating newRating() {
        return Rating.builder()
                .average(6.5D)
                .build();
    }

    private static Image newImage(final int id) {
        return Image.builder()
                .id(id)
                .type("Type " + id)
                .main(id == 1 ? true : false)
                .resolutions(ImageResolutions.builder()
                        .medium(ImageResolution.builder()
                                .height(600)
                                .width(800)
                                .url("https://someimageurl.com")
                                .build())
                        .original(ImageResolution.builder()
                                .height(1200)
                                .width(1600)
                                .url("https://someimageurl.com")
                                .build())
                        .build())
                .build();
    }

    private static Show.EmbeddedResource newShowEmbeddedResources(final Show.EmbeddedType... embeddedTypes) {
        if (Objects.isNull(embeddedTypes)) {
            return null;
        }

        final Show.EmbeddedResource.EmbeddedResourceBuilder builder = Show.EmbeddedResource.builder();
        for (int i = 0; i < embeddedTypes.length; ++i) {
            if (Objects.isNull(embeddedTypes[i])) {
                continue;
            }

            if (embeddedTypes[i] == Show.EmbeddedType.CAST) {
                builder.cast(PersonTestDataHelper.newCastMembers());
            } else if (embeddedTypes[i] == Show.EmbeddedType.EPISODES) {
                builder.episodes(newListOfEpisodes());
            } else if (embeddedTypes[i] == Show.EmbeddedType.NEXT_EPISODE) {
                builder.nextEpisode(newEpisode(1, 1, 4, (Episode.EmbeddedType) null));
            } else if (embeddedTypes[i] == Show.EmbeddedType.PREVIOUS_EPISODE) {
                builder.previousEpisode(newEpisode(1, 1, 2, (Episode.EmbeddedType) null));
            } else {
                throw new IllegalStateException("Unrecognized Show Embedded Resource Type: " + embeddedTypes[i]);
            }
        }
        return builder.build();
    }

    private static ShowResult newShowResult(final double score, final int id) {
        return ShowResult.builder()
                .score(score)
                .show(newShow(id))
                .build();
    }

    private static Schedule newSchedule() {
        return Schedule.builder()
                .days(List.of("Sunday"))
                .time("22:00")
                .build();
    }

    public static Network newNetwork() {
        return Network.builder()
                .country(newCountry())
                .id(8)
                .name("HBO")
                .officialSite("https://www.hbo.com/")
                .build();
    }

    private static Map<String, Object> newExternals() {
        return Map.of("tvrage", 30124,
                "thetvdb", 220411,
                "imdb", "tt1723816");
    }
}
