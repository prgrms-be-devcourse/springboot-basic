package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO `user` (user_id, username) VALUES (:userId, :username)";
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("userId", user.userId().toString());
        paramMap.put("username", user.username());

        jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT user_id, username FROM `user` ORDER BY created_at";

        return jdbcTemplate.query(sql, toRowMapper());
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT user_id, username FROM `user` WHERE username = :username";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);

        return jdbcTemplate.queryForObject(sql, paramMap, toRowMapper());
    }

    @Override
    public List<User> getUserListWithVoucherAssigned() {
        String sql = """
                       SELECT
                           DISTINCT u.user_id, u.username
                       FROM `user` u
                           JOIN `wallet` w ON u.user_id = w.user_id
                       WHERE w.assigned_time IS NOT NULL
                           AND w.unassigned_time IS NULL
                """;

        return jdbcTemplate.query(sql, toRowMapper());
    }

    @Override
    public User getUserByVoucherId(Voucher voucher) {
        String sql = """
                    SELECT
                        u.user_id, u.username
                    FROM `user` u
                        INNER JOIN `wallet` w ON u.user_id = w.user_id
                    WHERE w.voucher_id = :voucher_id
                """;

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucher_id", voucher.voucherId().toString());

        return jdbcTemplate.queryForObject(sql, paramMap, toRowMapper());
    }

    private static RowMapper<User> toRowMapper() {
        return (rs, rowNum) -> {
            UUID userId = UUID.fromString(rs.getString("user_id"));
            String receivedUsername = rs.getString("username");

            return new User(userId, receivedUsername);
        };
    }
}