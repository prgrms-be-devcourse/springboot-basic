package org.prgrms.dev.wallet.repository;

import org.prgrms.dev.customer.domain.Customer;
import org.prgrms.dev.exception.NotInsertException;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.prgrms.dev.wallet.domain.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"dev"})
public class JdbcWalletRepository implements WalletRepository {
    private static final int SUCCESS = 1;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, name, email, createdAt);
    };

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String voucherType = resultSet.getString("voucher_type");
        long voucherDiscount = resultSet.getLong("discount");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return VoucherType.getVoucherType(voucherType, voucherId, voucherDiscount, createdAt);
    };

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID walletId = toUUID(resultSet.getBytes("wallet_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Wallet(walletId, customerId, voucherId, createdAt);
    };

    final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public List<Voucher> findVoucherByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT v.voucher_id, v.voucher_type, v.discount, v.created_at " +
                "FROM vouchers v " +
                "JOIN wallets w " +
                "ON v.voucher_id = w.voucher_id " +
                "WHERE w.customer_id = UUID_TO_BIN(:customerId);",
            Collections.singletonMap("customerId", customerId.toString().getBytes())
            , voucherRowMapper);
    }

    @Override
    public List<Voucher> findNoVoucherByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT * FROM vouchers " +
                "WHERE voucher_id NOT IN (SELECT voucher_id FROM wallets WHERE customer_id = UUID_TO_BIN(:customerId));",
            Collections.singletonMap("customerId", customerId.toString().getBytes())
            , voucherRowMapper);
    }

    @Override
    public List<Customer> findCustomerByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("SELECT c.customer_id, c.name, c.email, c.last_login_at, c.created_at " +
                "FROM customers c " +
                "JOIN wallets w " +
                "ON c.customer_id = w.customer_id " +
                "WHERE w.voucher_id = UUID_TO_BIN(:voucherId);",
            Collections.singletonMap("voucherId", voucherId.toString().getBytes())
            , customerRowMapper);
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM wallets WHERE wallet_id = UUID_TO_BIN(:walletId)",
                Collections.singletonMap("walletId", walletId.toString().getBytes()),
                walletRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int insert(Wallet wallet) {
        Map<String, Object> params = Map.of("walletId", wallet.getWalletId().toString().getBytes(),
            "customerId", wallet.getCustomerId().toString().getBytes(),
            "voucherId", wallet.getVoucherId().toString().getBytes(),
            "createdAt", wallet.getCreatedAt());

        int insert = jdbcTemplate.update("INSERT INTO wallets(wallet_id, customer_id, voucher_id, created_at) " +
            "VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId), :createdAt)", params);
        if (insert != SUCCESS) {
            throw new NotInsertException("Noting was inserted");
        }
        return insert;
    }

    @Override
    public void deleteById(UUID walletId) {
        jdbcTemplate.update("DELETE FROM wallets WHERE wallet_id = UUID_TO_BIN(:walletId)", Collections.singletonMap("walletId", walletId.toString().getBytes()));
    }
}
