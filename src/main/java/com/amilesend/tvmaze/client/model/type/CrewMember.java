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

import com.amilesend.tvmaze.client.model.Person;
import lombok.Builder;
import lombok.Data;

/** Describes a production crew member. */
@Builder
@Data
public class CrewMember {
    /** The credited job role. */
    private final String type;
    /** Flag indicator if the crew member was a guest. */
    private final String guestCrewType;
    /** The associated person. */
    private final Person person;
}
