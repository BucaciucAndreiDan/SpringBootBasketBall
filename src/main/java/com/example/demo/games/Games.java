package com.example.demo.games;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Games {

    private Integer id;

    @NotBlank
    private final Date apiDate;

    @NotBlank
    private final Date requestDate;

    @NotBlank
    private List<Game> games;

    public Games(@NotBlank Date apiDate, @NotBlank Date requestDate) {
        this.apiDate = apiDate;
        this.requestDate = requestDate;
    }

    public Games(int id, @NotBlank Date apiDate, @NotBlank Date requestDate) {
        this(apiDate, requestDate);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id != null)
            throw new IllegalStateException("element already set");
        this.id = id;
    }

    public Date getApiDate() {
        return apiDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        if (this.games != null)
            throw new IllegalStateException("element already set");
        this.games = Collections.unmodifiableList(games);
    }


}
