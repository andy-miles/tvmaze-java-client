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
package com.amilesend.tvmaze.client.model.type;

import lombok.Builder;
import lombok.Data;

/**
 * Describes a single image.
 *
 * @see ImageResolutions
 * @see com.amilesend.tvmaze.client.model.Image
 */
@Builder
@Data
public class ImageResolution {
    /** The URL to fetch the image content. */
    private final String url;
    /** The image width in pixels. */
    private final int width;
    /** The image height in pixels. */
    private final int height;
}
