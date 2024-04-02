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
package com.amilesend.tvmaze.client.model.type;

import com.amilesend.tvmaze.client.model.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/** Describes a character in a TV show or episode. */
@SuperBuilder
@Getter
@ToString(callSuper = true)
public class Character extends Resource<Character, ResourceLink> {
    /** The TVMaze website url associated with the character. */
    private final String url;
    /** The character name. */
    private final String name;
    /** Links to images associated with the character. */
    private final ImageUrl image;
    /** Flag indicator if the cast member playing the role represents themself. */
    @SerializedName("self")
    private final boolean isSelf;
    /** Flag indicator if the cast member playing the role is just lending their voice. */
    @SerializedName("voice")
    private final boolean isVoice;
}
