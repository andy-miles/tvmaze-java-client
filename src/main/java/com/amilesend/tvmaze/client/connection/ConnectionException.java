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
package com.amilesend.tvmaze.client.connection;

/** Defines the exception thrown from {@link Connection}. */
public class ConnectionException extends RuntimeException {
    /**
     * Creates a new {@code TvMazeConnectionException}.
     *
     * @param msg the exception message
     */
    public ConnectionException(final String msg) {
        super(msg);
    }

    /**
     * Creates a new {@code TvMazeConnectionException}.
     *
     * @param msg the exception message
     * @param cause the cause of the exception
     */
    public ConnectionException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
