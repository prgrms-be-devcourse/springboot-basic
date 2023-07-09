package com.programmers.wallet.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.UUID;

@Repository
public class JdbcWalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void updateCustomerId(UUID customerId, UUID voucherId) {
        String sql = "update vouchers set customer_id = ? WHERE id = ?";

        jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
    }
}
