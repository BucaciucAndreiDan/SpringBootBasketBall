package com.example.demo.games;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GamesDetailsService {

    @Autowired
    private GamesDataAccessService gamesDataAccessService;

    @Transactional
    public Games loadUserByUsername(String userName) throws UsernameNotFoundException {
        Games games = gamesDataAccessService.selectLast();
        if (games == null)
            return null;
        else
            return games;
    }
}