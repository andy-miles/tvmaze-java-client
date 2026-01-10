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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SerializedResource {
    public static final SerializedResource IMAGE_LIST = new SerializedResource("/ImageList.json");
    public static final SerializedResource UPDATES = new SerializedResource("/Updates.json");

    private final String resourcePath;

    public InputStream getResource() {
        return new BufferedInputStream(this.getClass().getResourceAsStream(resourcePath));
    }

    public byte[] toGzipCompressedBytes() throws IOException {
        final byte[] uncompressed = getResource().readAllBytes();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(uncompressed.length);
        try(final GZIPOutputStream gos = new GZIPOutputStream(baos)) {
            gos.write(uncompressed);
        } finally {
            baos.close();
        }

        return baos.toByteArray();
    }

    @UtilityClass
    public static class Show {
        public static final SerializedResource ALIAS_LIST = new SerializedResource("/Show/AliasList.json");
        public static final SerializedResource ALL_EMBEDDED_TYPES = new SerializedResource("/Show/AllEmbeddedTypes.json");
        public static final SerializedResource EMBEDDED_EPISODES = new SerializedResource("/Show/EmbeddedEpisodes.json");
        public static final SerializedResource LIST = new SerializedResource("/Show/List.json");
        public static final SerializedResource RESULT_LIST = new SerializedResource("/Show/ResultList.json");
        public static final SerializedResource SEASON_LIST = new SerializedResource("/Show/SeasonList.json");
        public static final SerializedResource SHOW = new SerializedResource("/Show/Show.json");
    }

    @UtilityClass
    public static class Episode {
        public static final SerializedResource ALTERNATE_EPISODES_LIST = new SerializedResource("/Episode/AlternateEpisodesList.json");
        public static final SerializedResource ALTERNATE_LIST_EMBEDDED_ALTERNATE_EPISODES = new SerializedResource("/Episode/AlternateListEmbeddedAlternateEpisodes.json");
        public static final SerializedResource ALTERNATE_LIST_LIST = new SerializedResource("/Episode/AlternateListList.json");
        public static final SerializedResource EMBEDDED_SHOW = new SerializedResource("/Episode/EmbeddedShow.json");
        public static final SerializedResource EPISODE = new SerializedResource("/Episode/Episode.json");
        public static final SerializedResource LIST = new SerializedResource("/Episode/List.json");
        public static final SerializedResource LIST_EMBEDDED_GUEST_CAST = new SerializedResource("/Episode/ListEmbeddedGuestCast.json");
    }

    @UtilityClass
    public static class People {
        public static final SerializedResource CAST_CREDIT_LIST_EMBEDDED_EPISODE = new SerializedResource("/People/CastCreditListEmbeddedEpisode.json");
        public static final SerializedResource CAST_CREDIT_LIST_EMBEDDED_SHOW = new SerializedResource("/People/CastCreditListEmbeddedShow.json");
        public static final SerializedResource CAST_MEMBER_LIST = new SerializedResource("/People/CastMemberList.json");
        public static final SerializedResource CREW_CREDIT_LIST_EMBEDDED_SHOW = new SerializedResource("/People/CrewCreditListEmbeddedShow.json");
        public static final SerializedResource CREW_MEMBER_LIST = new SerializedResource("/People/CrewMemberList.json");
        public static final SerializedResource PERSON_EMBEDDED_CAST_CREDITS = new SerializedResource("/People/PersonEmbeddedCastCredits.json");
        public static final SerializedResource PERSON_LIST = new SerializedResource("/People/PersonList.json");
        public static final SerializedResource PERSON_RESULT_LIST = new SerializedResource("/People/PersonResultList.json");
    }
}
