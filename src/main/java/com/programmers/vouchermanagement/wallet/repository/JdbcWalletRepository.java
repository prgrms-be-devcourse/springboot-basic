package com.programmers.vouchermanagement.wallet.repository;

import com.programmers.vouchermanagement.global.common.JdbcRepositoryManager;
import com.programmers.vouchermanagement.wallet.domain.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Profile("default")
public class JdbcWalletRepository implements WalletRepository {

    private static final String CREATE = "INSERT INTO wallet(wallet_id, customer_id, voucher_id) VALUES(UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))";
    private static final String READ_CUSTOMER = "SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(?)";
    private static final String READ_VOUCHER = "SELECT * FROM wallet WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE = "DELETE FROM wallet WHERE customer_id = UUID_TO_BIN(?)";

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, index) -> {

        UUID walletId = JdbcRepositoryManager.bytesToUUID(resultSet.getBytes("wallet_id"));
        UUID customerId = JdbcRepositoryManager.bytesToUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = JdbcRepositoryManager.bytesToUUID(resultSet.getBytes("voucher_id"));

        return new Wallet(walletId, customerId, voucherId);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Wallet wallet) {
        jdbcTemplate.update(CREATE,
                wallet.getWalletId().toString(),
                wallet.getCustomerId().toString(),
                wallet.getVoucherId().toString());
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query(READ_CUSTOMER, walletRowMapper, customerId.toString());
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(READ_VOUCHER, walletRowMapper, voucherId.toString());
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        jdbcTemplate.update(DELETE, customerId.toString());
    }
}
