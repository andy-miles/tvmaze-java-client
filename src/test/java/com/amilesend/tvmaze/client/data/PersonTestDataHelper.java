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

import com.amilesend.tvmaze.client.model.CastCredit;
import com.amilesend.tvmaze.client.model.CrewCredit;
import com.amilesend.tvmaze.client.model.Person;
import com.amilesend.tvmaze.client.model.Resource;
import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.Character;
import com.amilesend.tvmaze.client.model.type.CrewMember;
import com.amilesend.tvmaze.client.model.type.PersonResult;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.amilesend.tvmaze.client.data.EpisodeTestDataHelper.newEpisode;
import static com.amilesend.tvmaze.client.data.ShowTestDataHelper.newCountry;
import static com.amilesend.tvmaze.client.data.ShowTestDataHelper.newImageUrl;
import static com.amilesend.tvmaze.client.data.ShowTestDataHelper.newShow;

@UtilityClass
public class PersonTestDataHelper {
    public static List<PersonResult> newPersonResults() {
        final List<PersonResult> persons = new ArrayList<>(3);
        for (int i = 1; i <= 3; ++i) {
            persons.add(PersonResult.builder()
                    .person(newPerson(i))
                    .score(100D - (12.2 * i))
                    .build());
        }

        return persons;
    }

    public static List<Person> newPersonList() {
        final List<Person> persons = new ArrayList<>(3);
        for (int i = 1; i <= 3; ++i) {
            persons.add(newPerson(i));
        }

        return persons;
    }

    public static Person newPerson(final int id, final Person.EmbeddedType... embeddedTypes) {
        return Person.builder()
                .birthday(LocalDate.of(1950, 01,15))
                .country(newCountry())
                .deathday(LocalDate.of(2020, 02, 12))
                .embeddedResource(newPersonEmbeddedResources(embeddedTypes))
                .gender("Male")
                .id(id)
                .image(newImageUrl())
                .links(Map.of(Resource.SELF_RESOURCE_LINK_TYPE, ResourceLink.builder().href("http://self.url").build()))
                .name("Person Name " + id)
                .updated(123456789L)
                .url("https://www.someurl.com")
                .build();
    }

    public static List<CastMember> newCastMembers() {
        final List<CastMember> castMembers = new ArrayList<>(3);
        for (int i = 1; i <= 3; ++i) {
            castMembers.add(CastMember.builder()
                    .character(newCharacter(i))
                    .person(newPerson(i))
                    .build());
        }

        return castMembers;
    }

    public static List<CastCredit> newCastCredits(CastCredit.EmbeddedType... embeddedResources) {
        final List<CastCredit> castCredits = new ArrayList<>(3);
        for (int i = 1; i<= 3; ++i) {
            castCredits.add(CastCredit.builder()
                            .embeddedResource(newCastCreditEmbeddedResources(embeddedResources))
                            .isSelf(false)
                            .isVoice(false)
                            .links(newCastCreditLinks(i, embeddedResources))
                            .build());
        }

        return castCredits;
    }

    public static List<CrewMember> newCrewMembers() {
        final List<CrewMember> crewMembers = new ArrayList<>(3);
        for (int i = 1; i <=3; ++i) {
            crewMembers.add(CrewMember.builder()
                    .person(newPerson(i))
                    .type("CrewMemberTypeValue")
                    .build());
        }

        return crewMembers;
    }

    public static List<CrewCredit> newCrewCredits(final CrewCredit.EmbeddedType... embeddedTypes) {
        final List<CrewCredit> crewCredits = new ArrayList<>(3);
        for (int i = 1; i<= 3; ++i) {
            final Map<String, ResourceLink> links = Map.of(
                    CrewCredit.ResourceLinkType.SHOW, ResourceLink.builder()
                            .href("https://www.someshow/" + i)
                            .name("Show Title " + i)
                            .build());
            crewCredits.add(CrewCredit.builder()
                    .embeddedResource(newCrewCreditEmbeddedResources(embeddedTypes))
                    .links(links)
                    .type("Crew Type")
                    .build());
        }

        return crewCredits;
    }

    private static Map<String, ResourceLink> newCastCreditLinks(
            final int index,
            final CastCredit.EmbeddedType... embeddedResources) {
        final Map<String, ResourceLink> links = new LinkedHashMap<>(2);
        // Character is always included
        links.put(CastCredit.ResourceLinkType.CHARACTER, ResourceLink.builder()
                .href("https://www.somecharacter/" + index)
                .name("Character " + index)
                .build());

        // By default include a show link
        if (Objects.isNull(embeddedResources) | embeddedResources.length == 0) {
            links.put(CastCredit.ResourceLinkType.SHOW,  ResourceLink.builder()
                    .href("https://www.someshow/" + index)
                    .name("Show Title " + index)
                    .build());
            return links;
        }

        // If explicitly defined, provide the link accordingly
        for (int i = 0; i < embeddedResources.length; ++i) {
            if (embeddedResources[i] == CastCredit.EmbeddedType.SHOW) {
                links.put(CastCredit.ResourceLinkType.SHOW, ResourceLink.builder()
                        .href("https://www.someshow/" + index)
                        .name("Show Title " + index)
                        .build());
            } else if (embeddedResources[i] == CastCredit.EmbeddedType.EPISODE) {
                links.put(CastCredit.ResourceLinkType.EPISODE, ResourceLink.builder()
                        .href("https://www.someepisode/" + index)
                        .name("Episode Title " + index)
                        .build());
            } else {
                throw new IllegalStateException("Unrecognized CrewCredit embedded type: " + embeddedResources[i]);
            }
        }

        return links;
    }

    private static CastCredit.EmbeddedResource newCastCreditEmbeddedResources(
            final CastCredit.EmbeddedType... embeddedTypes) {
        if (Objects.isNull(embeddedTypes) || embeddedTypes.length == 0) {
            return null;
        }

        final CastCredit.EmbeddedResource.EmbeddedResourceBuilder builder = CastCredit.EmbeddedResource.builder();
        for (int i = 0; i < embeddedTypes.length; ++i) {
            if (Objects.isNull(embeddedTypes[i])) {
                continue;
            }

            if (embeddedTypes[i] == CastCredit.EmbeddedType.SHOW) {
                builder.show(newShow());
            } else if (embeddedTypes[i] == CastCredit.EmbeddedType.EPISODE){
                builder.episode(newEpisode(1, 1, 1));
            } else {
                throw new IllegalStateException("Unrecognized embedded resource yype: " + embeddedTypes[i]);
            }
        }

        return builder.build();
    }

    private static CrewCredit.EmbeddedResource newCrewCreditEmbeddedResources(
            final CrewCredit.EmbeddedType... embeddedTypes) {
        if (Objects.isNull(embeddedTypes) || embeddedTypes.length == 0) {
            return null;
        }

        final CrewCredit.EmbeddedResource.EmbeddedResourceBuilder builder = CrewCredit.EmbeddedResource.builder();
        for (int i = 0; i < embeddedTypes.length; ++i) {
            if (Objects.isNull(embeddedTypes[i])) {
                continue;
            }

            if(embeddedTypes[i] == CrewCredit.EmbeddedType.SHOW) {
                builder.show(newShow());
            } else {
                throw new IllegalStateException("Unrecognized Show Embedded Resource Type: " + embeddedTypes[i]);
            }
        }

        return builder.build();
    }



    private static Character newCharacter(final int id) {
        return Character.builder()
                .id(id)
                .image(newImageUrl())
                .isSelf(false)
                .isVoice(false)
                .links(Map.of("self", ResourceLink.builder().href("http://character.com").build()))
                .name("Character " + id)
                .url("http://www.someurl.com")
                .build();
    }

    private static Person.EmbeddedResource newPersonEmbeddedResources(final Person.EmbeddedType... embeddedTypes) {
        if (Objects.isNull(embeddedTypes) || embeddedTypes.length == 0) {
            return null;
        }

        final Person.EmbeddedResource.EmbeddedResourceBuilder builder = Person.EmbeddedResource.builder();
        for (int i = 0; i < embeddedTypes.length; ++i) {
            if (Objects.isNull(embeddedTypes[i])) {
                continue;
            }

            if (embeddedTypes[i] == Person.EmbeddedType.CAST_CREDITS) {
                builder.castCredits(newCastCredits());
            } else {
                throw new IllegalStateException("Unrecognized Person Embedded Resource Type: " + embeddedTypes[i]);
            }
        }

        return builder.build();
    }
}
