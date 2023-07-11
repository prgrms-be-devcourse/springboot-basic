package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.Wallet;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcWalletRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Wallet wallet) {
        String sql = "INSERT INTO wallet (voucher_id, user_id) VALUES (:voucherId, :userId)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", wallet.voucherId().toString());
        paramMap.put("userId", wallet.userId().toString());
        jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void free(Wallet wallet) {
        String sql = "UPDATE wallet SET unassigned_time = CURRENT_TIMESTAMP WHERE voucher_id = :voucherId AND user_id = :userId";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", wallet.voucherId().toString());
        paramMap.put("userId", wallet.userId().toString());
        jdbcTemplate.update(sql, paramMap);
    }
}
