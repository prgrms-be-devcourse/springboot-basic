package com.programmers.springbootbasic.domain.wallet.repository;

import com.programmers.springbootbasic.common.utils.UUIDConverter;
import com.programmers.springbootbasic.domain.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WalletRepository {
    private static final String INSERT_QUERY = "INSERT INTO wallets(email, voucher_id) VALUES(?, UUID_TO_BIN(?))";
    private static final String SELECT_BY_CUSTOMER_EMAIL_QUERY = "SELECT * FROM wallets WHERE email = ?";
    private static final String SELECT_BY_VOUCHER_ID_QUERY = "SELECT * FROM wallets WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String SELECT_BY_VALUES_QUERY = "SELECT * FROM wallets WHERE email = ? AND voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE_BY_VALUES_QUERY = "DELETE FROM wallets WHERE email = ? AND voucher_id = UUID_TO_BIN(?)";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE wallets";

    private static final RowMapper<Wallet> ROW_MAPPER = (resultSet, rowNum) -> {
        String email = resultSet.getString("email");
        UUID voucherId = UUIDConverter.toUUID(resultSet.getBytes("voucher_id"));
        return Wallet.builder()
                .email(email)
                .voucherId(voucherId)
                .build();
    };

    private final JdbcTemplate jdbcTemplate;

    public Wallet save(Wallet wallet) {
        jdbcTemplate.update(INSERT_QUERY, wallet.getEmail(),
                wallet.getVoucherId().toString().getBytes());
        return wallet;
    }

    public List<Wallet> findByCustomerEmail(String email) {
        return jdbcTemplate.query(SELECT_BY_CUSTOMER_EMAIL_QUERY, ROW_MAPPER, email);
    }

    public List<Wallet> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(SELECT_BY_VOUCHER_ID_QUERY, ROW_MAPPER, voucherId.toString().getBytes());
    }

    public Optional<Wallet> findByValues(String email, UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_VALUES_QUERY, ROW_MAPPER,
                    email,
                    voucherId.toString().getBytes()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(Wallet wallet) {
        jdbcTemplate.update(DELETE_BY_VALUES_QUERY,
                wallet.getEmail(),
                wallet.getVoucherId().toString().getBytes());
    }

    public void deleteAll() {
        jdbcTemplate.execute(TRUNCATE_TABLE);
    }
}
