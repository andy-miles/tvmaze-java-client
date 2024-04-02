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
package com.amilesend.tvmaze.client.parse.parser;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Parsers {
    public static final AliasListParser ALIAS_LIST_PARSER = new AliasListParser();
    public static final AlternateEpisodeListParser ALTERNATE_EPISODE_LIST_PARSER = new AlternateEpisodeListParser();
    public static final AlternateListParser ALTERNATE_LIST_PARSER = new AlternateListParser();
    public static final AlternateListListParser ALTERNATE_LIST_LIST_PARSER = new AlternateListListParser();
    public static final CastCreditListParser CAST_CREDIT_LIST_PARSER = new CastCreditListParser();
    public static final CastMemberListParser CAST_MEMBER_LIST_PARSER = new CastMemberListParser();
    public static final CrewCreditListParser CREW_CREDIT_LIST_PARSER = new CrewCreditListParser();
    public static final CrewMemberListParser CREW_MEMBER_LIST_PARSER = new CrewMemberListParser();
    public static final EpisodeListParser EPISODE_LIST_PARSER = new EpisodeListParser();
    public static final EpisodeParser EPISODE_PARSER = new EpisodeParser();
    public static final ImageListParser IMAGE_LIST_PARSER = new ImageListParser();
    public static final PersonListParser PERSON_LIST_PARSER = new PersonListParser();
    public static final PersonParser PERSON_PARSER = new PersonParser();
    public static final PersonResultListParser PERSON_RESULT_LIST_PARSER = new PersonResultListParser();
    public static final SeasonListParser SEASON_LIST_PARSER = new SeasonListParser();
    public static final ShowParser SHOW_PARSER = new ShowParser();
    public static final ShowListParser SHOW_LIST_PARSER = new ShowListParser();
    public static final ShowResultListParser SHOW_RESULT_LIST_PARSER = new ShowResultListParser();
    public static final UpdatesParser UPDATES_PARSER = new UpdatesParser();
}
