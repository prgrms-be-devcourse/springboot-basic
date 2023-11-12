package com.prgrms.springbasic.domain.wallet.repository;

import com.prgrms.springbasic.domain.customer.entity.Customer;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.wallet.entity.Wallet;
import com.prgrms.springbasic.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcWalletRepository {

    private static final String INSERT_QUERY = "INSERT INTO wallet(wallet_id, customer_id, voucher_id) VALUES(UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))";
    private static final String FIND_BY_CUSTOMER_AND_VOUCHER_ID = "SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(?) AND voucher_id = UUID_TO_BIN(?)";
    private static final String FIND_VOUCHERS_BY_CUSTOMER_ID = "SELECT V.voucher_id, V.discount_type, V.discount_value FROM wallet W JOIN vouchers V on W.voucher_id = V.voucher_id WHERE customer_id = UUID_TO_BIN(?)";
    private static final String FIND_CUSTOMERS_BY_VOUCHER_ID = "SELECT C.customer_id, C.name, C.email FROM wallet W JOIN customers C on W.customer_id = C.customer_id WHERE voucher_id = UUID_TO_BIN(?)";
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

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = UUIDUtils.toUUID(resultSet.getBytes("voucher_id"));
        String discountType = resultSet.getString("discount_type");
        long discountValue = resultSet.getLong("discount_value");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return Voucher.createVoucher(voucherId, discountType, discountValue, createdAt);
    };

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = UUIDUtils.toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new Customer(customerId, customerName, email);
    };

    public Wallet saveWallet(Wallet wallet) {
        jdbcTemplate.update(INSERT_QUERY,
                wallet.getWalletId().toString().getBytes(),
                wallet.getCustomerId().toString().getBytes(),
                wallet.getVoucherId().toString().getBytes());
        return wallet;
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return jdbcTemplate.query(FIND_VOUCHERS_BY_CUSTOMER_ID, voucherRowMapper, customerId.toString().getBytes());
    }

    public List<Customer> findCustomersByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(FIND_CUSTOMERS_BY_VOUCHER_ID, customerRowMapper, voucherId.toString().getBytes());
    }

    public Optional<Wallet> findWalletByCustomerAndVoucher(UUID customer_id, UUID voucher_id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_CUSTOMER_AND_VOUCHER_ID,
                    walletRowMapper,
                    customer_id.toString().getBytes(),
                    voucher_id.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.info("Voucher not found", e);
            return Optional.empty();
        }
    }

    public void deleteWallet(Wallet wallet) {
        jdbcTemplate.update(DELETE_WALLET,
                wallet.getWalletId().toString().getBytes());
    }
}
