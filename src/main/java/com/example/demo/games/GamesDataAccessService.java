package com.example.demo.games;


import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static com.example.demo.db.Queries.*;

@Repository
public class GamesDataAccessService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GamesDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Games selectLast() {
        List<Games> gamesList = jdbcTemplate.query(GET_LATEST_REQUEST, GAMES_ROW_MAPPER);
        if (gamesList == null || gamesList.isEmpty())
            return null;
        Games games = gamesList.get(0);
        List<Game> gameList = jdbcTemplate.query(GET_GAMES_BY_REQUEST_ID, GAME_ROW_MAPPER, games.getId());
        games.setGames(gameList);
        return games;
    }


    public void insert(Games games) {
        Integer id = jdbcTemplate.queryForObject(INSERT_GAME_REQUEST, Integer.class, games.getApiDate(), games.getRequestDate());
        if (id != null)
            games.setId(id);
        for (Game game : games.getGames()) {
            game.setRequestId(id);
            Integer id2 = jdbcTemplate.queryForObject(INSERT_GAME, Integer.class, id, game.getDate(), game.getStatus(), game.getCountry()
                    , game.getHomeTeam(), game.getAwayTeam(), game.getHomeScore(), game.getAwayScore());
            if (id2 != null)
                game.setId(id2);

        }
    }

    private static SimpleDateFormat apiDateParser = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat gameDateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
    private static SimpleDateFormat makeHttpUrl = new SimpleDateFormat("yyyy-MM-dd");

    public Games refreshData() {
        String data;
        String httpUrl="https://api-basketball.p.rapidapi.com/games?date="+makeHttpUrl.format(new Date());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(httpUrl))
                .header("x-rapidapi-key", "b3545b6888mshea56bdf6bfc92abp1156fejsnb9148097c240")
                .header("x-rapidapi-host", "api-basketball.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
        System.out.println(httpResponse.body());
        data = httpResponse.body();

//        data = example;

        JSONParser parser = new JSONParser(data);
        try {
            LinkedHashMap<String, Object> paredJson = (LinkedHashMap<String, Object>) parser.parse();

            LinkedHashMap<String, Object> parameters = (LinkedHashMap<String, Object>) paredJson.get("parameters");
            String string = (String) parameters.get("date");
            Date apiDate = apiDateParser.parse(string);

            Games games = new Games(apiDate, new Date());
            List<Game> gameList = new ArrayList<>();

            ArrayList<Object> responses = (ArrayList<Object>) paredJson.get("response");
            for (Object uncastedResponse : responses) {
                try {
                    LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) uncastedResponse;
                    string = (String) response.get("date");

                    Date date = gameDateParser.parse(string);

                    LinkedHashMap<String, Object> hashMap = (LinkedHashMap<String, Object>) response.get("status");
                    String status = (String) hashMap.get("long");

                    hashMap = (LinkedHashMap<String, Object>) response.get("country");
                    String country = (String) hashMap.get("name");

                    hashMap = (LinkedHashMap<String, Object>) response.get("teams");
                    LinkedHashMap<String, Object> hashMap2 = (LinkedHashMap<String, Object>) hashMap.get("home");
                    String homeTeam = (String) hashMap2.get("name");

                    hashMap2 = (LinkedHashMap<String, Object>) hashMap.get("away");
                    String awayTeam = (String) hashMap2.get("name");


                    hashMap = (LinkedHashMap<String, Object>) response.get("scores");
                    hashMap2 = (LinkedHashMap<String, Object>) hashMap.get("home");
                    BigInteger bigInteger = (BigInteger) hashMap2.get("total");

                    int homeScore = 0;
                    if (bigInteger!=null)
                        homeScore=bigInteger.intValue();

                    hashMap2 = (LinkedHashMap<String, Object>) hashMap.get("away");
                    bigInteger = (BigInteger) hashMap2.get("total");
                    int awayScore = 0;
                    if (bigInteger!=null)
                        awayScore=bigInteger.intValue();

                    Game game = new Game(date, status, country, homeTeam, awayTeam, homeScore, awayScore);
                    gameList.add(game);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
            games.setGames(gameList);
            insert(games);
            return games;
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

//    String example;
//
//    {
//        try {
//            example = new String(Files.readAllBytes(Paths.get("/home/thenok/Desktop/longStr")));
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
//    }

}
