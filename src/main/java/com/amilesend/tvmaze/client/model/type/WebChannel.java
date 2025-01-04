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
package com.amilesend.tvmaze.client.model.type;

import lombok.Builder;
import lombok.Data;

/** Describes the streaming network/site that distributes a TV show. */
@Builder
@Data
public class WebChannel {
    /** The web channel's unique identifier. */
    private final int id;
    /** The channel's name. */
    private final String name;
    /** The associated country. */
    private final Country country;
    /** The URL to the channel's website. */
    private final String officialSite;
}
