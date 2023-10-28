package com.programmers.springbootbasic.domain.user.infrastructure;

import com.programmers.springbootbasic.domain.user.domain.UserRepository;
import com.programmers.springbootbasic.domain.user.domain.entity.User;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final String TABLE_NAME = "users";
    private static final String ID = "id";
    private static final String NICKNAME = "nickname";
    private static final String BLOCKED = "blocked";

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper =
        (rs, rowNum) -> new User(rs.getLong(ID), rs.getString(NICKNAME), rs.getBoolean(BLOCKED));

    @Override
    public User save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                .prepareStatement(
                    String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", TABLE_NAME, NICKNAME,
                        BLOCKED),
                    new String[]{ID});
            ps.setString(1, entity.getNickname());
            ps.setBoolean(2, entity.isBlocked());
            return ps;
        }, keyHolder);

        // key 없으면 npe
        return new User(Objects.requireNonNull(keyHolder.getKey()).longValue(),
            entity.getNickname(), entity.isBlocked());
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> users = jdbcTemplate.query(
            String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, ID), userRowMapper, id);
        return users.stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(String.format("SELECT * FROM %s", TABLE_NAME), userRowMapper);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID),
            id);
    }

    @Override
    public int update(User entity) {
        return jdbcTemplate.update(
            String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, NICKNAME,
                BLOCKED, ID),
            entity.getNickname(), entity.isBlocked(), entity.getId());
    }

    @Override
    public List<User> findBlacklistedUsers() {
        return jdbcTemplate.query(
            String.format("SELECT * FROM %s WHERE %s = true", TABLE_NAME, BLOCKED), userRowMapper);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, NICKNAME), userRowMapper,
            nickname).stream().findFirst();
    }
}
