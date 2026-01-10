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
package com.amilesend.tvmaze.client.model;

import com.amilesend.tvmaze.client.model.type.ImageResolutions;
import com.amilesend.tvmaze.client.model.type.ResourceLink;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/** Describes an image associated with a person, show, or episode. */
@SuperBuilder
@Getter
@Setter
@ToString
public class Image extends Resource<Image, ResourceLink> {
    /** The image type (e.g., poster). */
    private final String type;
    /** Flag indicator to signify if the image is the primary/default one. */
    private final boolean main;
    /** The URLs and dimensions for the image content. */
    private final ImageResolutions resolutions;
}
