package com.example.demo.db;

import com.example.demo.games.Game;
import com.example.demo.games.Games;
import com.example.demo.users.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Queries {
    private static class T {
        private static class USERS {
            private static final String NAME = "users";

            private static class C {
                private static final String ID = "id";
                private static final String USERNAME = "username";
                private static final String PASSWORD = "password";
                private static final String ENABLED = "enabled";
                private static final String AUTHORITY = "authority";
            }
        }

        private static class GAME_REQUESTS {
            private static final String NAME = "game_requests";

            private static class C { //column
                private static final String ID = "id";
                private static final String API_DATE = "api_date";
                private static final String REQUEST_DATE_TIME = "request_date_time";
            }
        }


        private static class GAMES {
            private static final String NAME = "games";

            private static class C { //column
                private static final String ID = "id";
                private static final String REQUEST_ID = "request_id";
                private static final String DATE = "date";
                private static final String STATUS = "status";
                private static final String COUNTRY = "country";
                private static final String HOME_TEAM = "home_team";
                private static final String AWAY_TEAM = "away_team";
                private static final String HOME_SCORE = "home_score";
                private static final String AWAY_SCORE = "away_score";
            }
        }
    }


    public static String GET_USER_BY_NAME = String.format("SELECT %s,%s,%s,%s,%s FROM %s WHERE %s=?;"
            , T.USERS.C.ID, T.USERS.C.USERNAME, T.USERS.C.PASSWORD, T.USERS.C.ENABLED, T.USERS.C.AUTHORITY
            , T.USERS.NAME
            , T.USERS.C.USERNAME);


    public static String GET_LATEST_REQUEST = String.format("SELECT %s,%s,%s FROM %s ORDER BY %s desc, %s desc;"
            , T.GAME_REQUESTS.C.ID, T.GAME_REQUESTS.C.API_DATE, T.GAME_REQUESTS.C.REQUEST_DATE_TIME
            , T.GAME_REQUESTS.NAME
            , T.GAME_REQUESTS.C.REQUEST_DATE_TIME, T.GAME_REQUESTS.C.ID);


    public static String GET_GAMES_BY_REQUEST_ID = String.format("SELECT %s,%s,%s,%s,%s,%s,%s,%s,%s FROM %s WHERE %s=?;"
            , T.GAMES.C.ID, T.GAMES.C.REQUEST_ID, T.GAMES.C.DATE, T.GAMES.C.STATUS, T.GAMES.C.COUNTRY, T.GAMES.C.HOME_TEAM, T.GAMES.C.AWAY_TEAM, T.GAMES.C.HOME_SCORE, T.GAMES.C.AWAY_SCORE
            , T.GAMES.NAME
            , T.GAMES.C.REQUEST_ID);


    public static String INSERT_GAME_REQUEST = String.format("INSERT INTO %s(%s,%s) VALUES(?,?) RETURNING %s;"
            , T.GAME_REQUESTS.NAME
            , T.GAME_REQUESTS.C.API_DATE, T.GAME_REQUESTS.C.REQUEST_DATE_TIME
            , T.GAME_REQUESTS.C.ID);


    public static String INSERT_GAME = String.format("INSERT INTO %s(%s,%s,%s,%s,%s,%s,%s,%s) VALUES(?,?,?,?,?,?,?,?) RETURNING %s;"
            , T.GAMES.NAME
            , T.GAMES.C.REQUEST_ID, T.GAMES.C.DATE, T.GAMES.C.STATUS, T.GAMES.C.COUNTRY, T.GAMES.C.HOME_TEAM, T.GAMES.C.AWAY_TEAM, T.GAMES.C.HOME_SCORE, T.GAMES.C.AWAY_SCORE
            , T.GAMES.C.ID);

    public static RowMapper<User> USER_ROW_MAPPER = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt(T.USERS.C.ID);
            String userName = resultSet.getString(T.USERS.C.USERNAME);
            String password = resultSet.getString(T.USERS.C.PASSWORD);
            boolean enabled = resultSet.getBoolean(T.USERS.C.ENABLED);
            String authority = resultSet.getString(T.USERS.C.AUTHORITY);

            Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return authority;
                }
            };
            grantedAuthorities.add(grantedAuthority);
            return new User(id, userName, password, enabled, grantedAuthorities);
        }
    };


    public static RowMapper<Games> GAMES_ROW_MAPPER = new RowMapper<Games>() {
        @Override
        public Games mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt(T.GAME_REQUESTS.C.ID);
            Date apiDate = resultSet.getDate(T.GAME_REQUESTS.C.API_DATE);
            Date requestDateTime = resultSet.getDate(T.GAME_REQUESTS.C.REQUEST_DATE_TIME);

            return new Games(id, apiDate, requestDateTime);
        }
    };


    public static RowMapper<Game> GAME_ROW_MAPPER = new RowMapper<Game>() {
        @Override
        public Game mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt(T.GAMES.C.ID);
            int requestId = resultSet.getInt(T.GAMES.C.REQUEST_ID);
            Date date = resultSet.getDate(T.GAMES.C.DATE);
            String status = resultSet.getString(T.GAMES.C.STATUS);
            String country = resultSet.getString(T.GAMES.C.COUNTRY);
            String homeTeam = resultSet.getString(T.GAMES.C.HOME_TEAM);
            String awayTeam = resultSet.getString(T.GAMES.C.AWAY_TEAM);
            int homeScore = resultSet.getInt(T.GAMES.C.HOME_SCORE);
            int awayScore = resultSet.getInt(T.GAMES.C.AWAY_SCORE);

            return new Game(id, requestId, date, status, country, homeTeam, awayTeam, homeScore, awayScore);
        }
    };

}
