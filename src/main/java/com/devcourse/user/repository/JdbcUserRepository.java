package com.devcourse.user.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.UUID;

@Component
class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(String name) {
        int result = jdbcTemplate.update("INSERT INTO users(id, name) VALUES (?, ?)",
                UUID.randomUUID().toString(),
                name);
    }
}
