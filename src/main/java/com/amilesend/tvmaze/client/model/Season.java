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

import com.amilesend.tvmaze.client.model.type.ImageUrl;
import com.amilesend.tvmaze.client.model.type.Network;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/** Describes a TV show season. */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class Season extends Resource<Season, ResourceLink> {
    /** The associated TVMaze website URL. */
    private final String url;
    /** The season number. */
    private final int number;
    /** The season name. */
    private final String name;
    /** The number of episodes ordered for the season. */
    private final int episodeOrder;
    /** The date that the season initially aired. */
    private final LocalDate premiereDate;
    /** The date tha tthe season finished airing. */
    private final LocalDate endDate;
    /** The associated distribution network. */
    private final Network network;
    /** The associated image content.*/
    private final ImageUrl image;
}
