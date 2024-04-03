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

import com.amilesend.tvmaze.client.model.AlternateEpisode;
import com.amilesend.tvmaze.client.model.AlternateList;
import com.amilesend.tvmaze.client.model.CastCredit;
import com.amilesend.tvmaze.client.model.CrewCredit;
import com.amilesend.tvmaze.client.model.Episode;
import com.amilesend.tvmaze.client.model.Image;
import com.amilesend.tvmaze.client.model.Person;
import com.amilesend.tvmaze.client.model.Season;
import com.amilesend.tvmaze.client.model.Show;
import com.amilesend.tvmaze.client.model.type.Alias;
import com.amilesend.tvmaze.client.model.type.CastMember;
import com.amilesend.tvmaze.client.model.type.CrewMember;
import com.amilesend.tvmaze.client.model.type.PersonResult;
import com.amilesend.tvmaze.client.model.type.ShowResult;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

@UtilityClass
public class Parsers {
    public static final GsonParser<List<Alias>> ALIAS_LIST_PARSER = new ListParser<>(Alias.class);
    public static final GsonParser<List<AlternateEpisode>> ALTERNATE_EPISODE_LIST_PARSER = new ListParser<>(AlternateEpisode.class);
    public static final GsonParser<AlternateList> ALTERNATE_LIST_PARSER = new BasicParser<>(AlternateList.class);
    public static final GsonParser<List<AlternateList>> ALTERNATE_LIST_LIST_PARSER = new ListParser<>(AlternateList.class);
    public static final GsonParser<List<CastCredit>> CAST_CREDIT_LIST_PARSER = new ListParser<>(CastCredit.class);
    public static final GsonParser<List<CastMember>> CAST_MEMBER_LIST_PARSER = new ListParser<>(CastMember.class);
    public static final GsonParser<List<CrewCredit>> CREW_CREDIT_LIST_PARSER = new ListParser<>(CrewCredit.class);
    public static final GsonParser<List<CrewMember>> CREW_MEMBER_LIST_PARSER = new ListParser<>(CrewMember.class);
    public static final GsonParser<List<Episode>> EPISODE_LIST_PARSER = new ListParser<>(Episode.class);
    public static final GsonParser<Episode> EPISODE_PARSER = new BasicParser<>(Episode.class);
    public static final GsonParser<List<Image>> IMAGE_LIST_PARSER = new ListParser<>(Image.class);
    public static final GsonParser<List<Person>> PERSON_LIST_PARSER = new ListParser<>(Person.class);
    public static final GsonParser<Person> PERSON_PARSER = new BasicParser<>(Person.class);
    public static final GsonParser<List<PersonResult>> PERSON_RESULT_LIST_PARSER = new ListParser<>(PersonResult.class);
    public static final GsonParser<List<Season>> SEASON_LIST_PARSER = new ListParser<>(Season.class);
    public static final GsonParser<Show> SHOW_PARSER = new BasicParser<>(Show.class);
    public static final GsonParser<List<Show>> SHOW_LIST_PARSER = new ListParser<>(Show.class);
    public static final GsonParser<List<ShowResult>> SHOW_RESULT_LIST_PARSER = new ListParser<>(ShowResult.class);
    public static final GsonParser<Map<Integer, Long>> UPDATES_PARSER = new MapParser<>(Integer.class, Long.class);
}
