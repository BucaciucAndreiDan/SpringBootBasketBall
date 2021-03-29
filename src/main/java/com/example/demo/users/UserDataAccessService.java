package com.example.demo.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.db.Queries.GET_USER_BY_NAME;
import static com.example.demo.db.Queries.USER_ROW_MAPPER;

@Repository
public class UserDataAccessService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    User selectByName(String name) {
        List<User> users = jdbcTemplate.query(GET_USER_BY_NAME, new Object[]{name}, USER_ROW_MAPPER);
        if (users == null || users.isEmpty())
            return null;
        else
            return users.get(0);
    }
}
