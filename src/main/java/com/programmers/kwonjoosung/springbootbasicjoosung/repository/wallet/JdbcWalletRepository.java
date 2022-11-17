package com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JdbcWalletRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(UUID customerId, UUID voucherId) {
        final String sql = "INSERT INTO wallets (customer_id, voucher_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
    }

    public List<UUID> findByCustomerId(UUID customerId) {
        final String sql = "SELECT * FROM wallets WHERE customer_id = ? ORDER BY wallet_id";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> UUID.fromString(rs.getString("voucher_id")),
                customerId.toString());
    }

    public UUID findByVoucherId(UUID voucherId) {
        final String sql = "SELECT * FROM wallets WHERE voucher_id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> UUID.fromString(rs.getString("customer_id")),
                voucherId.toString());
    }

    public int delete(UUID voucherId) {
        final String sql = "DELETE FROM wallets WHERE voucher_id = ?";
        return jdbcTemplate.update(sql,voucherId.toString());
    }

}
