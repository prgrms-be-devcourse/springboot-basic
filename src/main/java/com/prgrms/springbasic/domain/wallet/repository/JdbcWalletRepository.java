package com.prgrms.springbasic.domain.wallet.repository;

import com.prgrms.springbasic.domain.wallet.entity.Wallet;
import com.prgrms.springbasic.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcWalletRepository {

    private static final String INSERT_QUERY = "INSERT INTO wallet(wallet_id, customer_id, voucher_id) VALUES(UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))";
    private static final String FIND_BY_CUSTOMER_AND_VOUCHER_ID = "SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(?) AND voucher_id = UUID_TO_BIN(?)";
    private static final String SELECT_WALLET_BY_CUSTOMER_ID = "SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(?)";
    private static final String SELECT_WALLET_BY_VOUCHER_ID = "SELECT * FROM wallet WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE_WALLET = "DELETE from wallet WHERE wallet_id = ?";
    private static final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, rowNum) -> {
        UUID walletId = UUIDUtils.toUUID(resultSet.getBytes("wallet_id"));
        UUID customer_id = UUIDUtils.toUUID(resultSet.getBytes("customer_id"));
        UUID voucher_id = UUIDUtils.toUUID(resultSet.getBytes("voucher_id"));
        return new Wallet(walletId, customer_id, voucher_id);
    };

    public Wallet saveWallet(Wallet wallet) {
        jdbcTemplate.update(INSERT_QUERY,
                wallet.getWallet_id().toString().getBytes(),
                wallet.getCustomer_id().toString().getBytes(),
                wallet.getVoucher_id().toString().getBytes());
        return wallet;
    }

    public List<Wallet> findWalletsByCustomerId(UUID customerId) {
        return jdbcTemplate.query(SELECT_WALLET_BY_CUSTOMER_ID, walletRowMapper, customerId.toString().getBytes());
    }

    public List<Wallet> findWalletsByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(SELECT_WALLET_BY_VOUCHER_ID, walletRowMapper, voucherId.toString().getBytes());
    }

    public Optional<Wallet> findWalletByCustomerAndVoucher(UUID customer_id, UUID voucher_id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_CUSTOMER_AND_VOUCHER_ID,
                    walletRowMapper,
                    customer_id.toString().getBytes(),
                    voucher_id.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Voucher not found", e);
            return Optional.empty();
        }
    }

    public void deleteWallet(Wallet wallet) {
        jdbcTemplate.update(DELETE_WALLET,
                wallet.getWallet_id().toString().getBytes());
    }
}
