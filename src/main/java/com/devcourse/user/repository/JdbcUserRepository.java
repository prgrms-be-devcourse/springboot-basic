package com.devcourse.user.repository;

import com.devcourse.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Component
class JdbcUserRepository implements UserRepository {
    private static final RowMapper<User> userMapper = (resultSet, resultNumber) -> {
        UUID id = UUID.fromString(resultSet.getString("id"));
        String name = resultSet.getString("name");
        return new User(id, name);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(String name) {
        jdbcTemplate.update("INSERT INTO users(id, name) VALUES (?, ?)",
                UUID.randomUUID().toString(),
                name);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userMapper);
    }
}
