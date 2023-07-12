package com.dev.bootbasic.user.repository;

import com.dev.bootbasic.user.domain.User;
import com.dev.bootbasic.voucher.domain.Voucher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final String INSERT_USER_COMMAND = "INSERT INTO users (id, name, created_at) VALUES (:id, :name, :createdAt)";
    private static final String SELECT_USER__BY_NAME_QUERY = "SELECT * FROM users WHERE id = :id";
    private static final String DELETE_USER_BY_NAME_COMMAND = "DELETE FROM users WHERE id = :id";
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
    public Optional<User> findById(UUID id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            User user = namedParameterJdbcTemplate.queryForObject(SELECT_USER__BY_NAME_QUERY, params, getUserRowMapper());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedParameterJdbcTemplate.update(DELETE_USER_BY_NAME_COMMAND, params);
    }

    private RowMapper<User> getUserRowMapper() {
        return (rs, rowNum) -> User.of(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getTimestamp("created_at").toLocalDateTime());
    }
}

