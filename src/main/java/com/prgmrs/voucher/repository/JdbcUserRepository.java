package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (user_id, username) VALUES (:userId, :username)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", user.getUserId().toString());
        paramMap.put("username", user.getUsername());

        jdbcTemplate.update(sql, paramMap);
    }
}