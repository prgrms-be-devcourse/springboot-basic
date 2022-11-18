package com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcWalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String TABLE_FIELD_CUSTOMER_ID = "customer_id";

    private static final String TABLE_FIELD_VOUCHER_ID = "voucher_id";

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean insertToWallet(UUID customerId, UUID voucherId) {
        final String sql = "INSERT INTO wallets (customer_id, voucher_id) VALUES (:customer_id, :voucher_id)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(TABLE_FIELD_CUSTOMER_ID, customerId)
                .addValue(TABLE_FIELD_VOUCHER_ID, voucherId);
        try {
            return jdbcTemplate.update(sql, parameters) == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public List<UUID> findVoucherIdsByCustomerId(UUID customerId) {
        final String sql = "SELECT * FROM wallets WHERE customer_id = :customer_id";
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue(TABLE_FIELD_CUSTOMER_ID, customerId);
        try { // 예외를 던지는게 좋은 건지 아니면 빈 값을 던지는게 좋은 건지?
            return jdbcTemplate.query(sql, parameter,
                    (rs, rowNum) -> UUID.fromString(rs.getString(TABLE_FIELD_VOUCHER_ID)));
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    public Optional<UUID> findCustomerIdByVoucherId(UUID voucherId) {
        final String sql = "SELECT * FROM wallets WHERE voucher_id = :voucher_id";
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue(TABLE_FIELD_VOUCHER_ID, voucherId);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameter,
                    (rs, rowNum) -> UUID.fromString(rs.getString(TABLE_FIELD_CUSTOMER_ID))));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean deleteVoucher(UUID voucherId) {
        final String sql = "DELETE FROM wallets WHERE voucher_id = :voucher_id";
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue(TABLE_FIELD_VOUCHER_ID, voucherId);
        try {
            return jdbcTemplate.update(sql, parameter) == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }

}
