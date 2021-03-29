package com.example.demo.controllers;


import com.example.demo.games.Games;
import com.example.demo.games.GamesDataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Game {


    @Autowired
    GamesDataAccessService gamesDataAccessService;

    @RequestMapping("/games.html")
    public String game(Model model) {
        Games games = gamesDataAccessService.selectLast();
        if (games!=null){
            model.addAttribute("gameDate", games.getApiDate());
            model.addAttribute("games", games.getGames());
        }
        return "games";
    }

    @RequestMapping(value = "/games.html", method = RequestMethod.GET, params = "action")
    public String refreshData(@RequestParam String action, Model model) {
        Games games = gamesDataAccessService.refreshData();
        if (games!=null){
        model.addAttribute("gameDate", games.getApiDate());
        model.addAttribute("games", games.getGames());
        }
        return "games";
    }


}