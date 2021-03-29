package com.example.demo.games;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class Game {

    private Integer id;
    private Integer requestId;

    @NotBlank
    private final Date date;

    @NotBlank
    private final String status;
    @NotBlank
    private final String country;
    @NotBlank
    private final String homeTeam;
    @NotBlank
    private final String awayTeam;

    private final int homeScore;
    private final int awayScore;


    public Game(@NotBlank Date date, @NotBlank String status, @NotBlank String country, @NotBlank String homeTeam, @NotBlank String awayTeam, int homeScore, int awayScore) {
        this.date = date;
        this.status = status;
        this.country = country;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }


    public Game(int id, int requestId, @NotBlank Date date, @NotBlank String status, @NotBlank String country, @NotBlank String homeTeam, @NotBlank String awayTeam, int homeScore, int awayScore) {
        this(date, status, country, homeTeam, awayTeam, homeScore, awayScore);
        this.id = id;
        this.requestId = requestId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id != null)
            throw new IllegalStateException("element already set");
        this.id = id;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        if (this.requestId != null)
            throw new IllegalStateException("element already set");
        this.requestId = requestId;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
    
}
