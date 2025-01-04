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

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/** Describes a television show episode. */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public abstract class EpisodeBase<T extends EpisodeBase, L> extends Resource<T, L> {
    /** The URL for the episode. */
    private final String url;
    /** The episode title. */
    private final String name;
    /** The associated season number of the episode. */
    private final int season;
    /** The associated episode numer within the season of the episode. */
    private final int number;
    /** The episode type. */
    private final String type;
    /** The date of the initial airing. */
    private final LocalDate airdate;
    /** The time of day of the initial airing. */
    private final LocalTime airtime;
    /** The UTC date and time of the initial airing. */
    private final LocalDateTime airstamp;
    /** The time in minutes of the episode. */
    private final int runtime;
}
