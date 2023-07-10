package com.dev.bootbasic.user.repository;

import com.dev.bootbasic.user.domain.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final String INSERT_USER_COMMAND = "INSERT INTO users (id, name, created_at) VALUES (:id, :name, :createdAt)";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcUserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User create(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("name", user.getName());
        params.addValue("createdAt", Timestamp.valueOf(user.getCreatedAt()));
        namedParameterJdbcTemplate.update(INSERT_USER_COMMAND, params);
        return user;
    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void deleteByName(String name) {

    }
}

