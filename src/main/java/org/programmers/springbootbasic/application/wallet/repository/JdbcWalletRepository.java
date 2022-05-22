package org.programmers.springbootbasic.application.wallet.repository;

import org.programmers.springbootbasic.application.customer.model.Customer;
import org.programmers.springbootbasic.core.exception.NotInsertException;
import org.programmers.springbootbasic.application.voucher.model.Voucher;
import org.programmers.springbootbasic.application.voucher.model.VoucherType;
import org.programmers.springbootbasic.application.wallet.domain.Wallet;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class JdbcWalletRepository implements WalletRepository {
    private static final int SUCCESS = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findVoucherByCustomerId(UUID customerId) {
        RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
            var voucherId = toUUID(resultSet.getBytes("voucher_id"));
            var discountValue = resultSet.getLong("discount_value");
            var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            var voucherType = resultSet.getString("voucher_type");

            return VoucherType.findByType(voucherType).create(voucherId, discountValue, createdAt);
        };

        return jdbcTemplate.query("SELECT v.voucher_id, v.voucher_type, v.discount_value, v.created_at " +
                        "FROM vouchers v " +
                        "JOIN wallets w " +
                        "ON v.voucher_id = w.voucher_id " +
                        "WHERE w.customer_id = UUID_TO_BIN(:customerId);",
                Collections.singletonMap("customerId", customerId.toString().getBytes())
                , voucherRowMapper);
    }

    @Override
    public List<Customer> findCustomerByVoucherId(UUID voucherId) {
        RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
            var customerId = toUUID(resultSet.getBytes("customer_id"));
            var name = resultSet.getString("name");
            var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

            return new Customer(customerId, name, createdAt);
        };

        return jdbcTemplate.query("SELECT c.customer_id, c.name, c.created_at " +
                        "FROM customers c " +
                        "JOIN wallets w " +
                        "ON c.customer_id = w.customer_id " +
                        "WHERE w.voucher_id = UUID_TO_BIN(:voucherId);",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes())
                , customerRowMapper);
    }

    @Override
    public Optional<Wallet> findById(UUID inputWalletId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM wallets WHERE wallet_id = UUID_TO_BIN(:walletId)",
                    Collections.singletonMap("walletId", inputWalletId.toString().getBytes()),
                    walletRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Wallet insert(Wallet wallet) {
        Map<String, Object> parameterMap = Map.of("walletId", wallet.getWalletId().toString().getBytes(),
                "customerId", wallet.getCustomerId().toString().getBytes(),
                "voucherId", wallet.getVoucherId().toString().getBytes());

        int insert = jdbcTemplate.update("INSERT INTO wallets(wallet_id, customer_id, voucher_id) VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))", parameterMap);
        if (insert != SUCCESS) {
            throw new NotInsertException("Noting was inserted");
        }

        return wallet;
    }

    @Override
    public void deleteById(UUID walletId) {
        jdbcTemplate.update("DELETE FROM wallets WHERE wallet_id = UUID_TO_BIN(:walletId)",
                Collections.singletonMap("walletId", walletId.toString().getBytes()));
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallets", Collections.emptyMap());
    }

    @Override
    public int getCountByWalletId(UUID walletId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wallets WHERE wallet_id = UUID_TO_BIN(:walletId)",
                Collections.singletonMap("walletId", walletId.toString().getBytes()), Integer.class);
    }

    private final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID walletId = toUUID(resultSet.getBytes("wallet_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));

        return new Wallet(walletId, customerId, voucherId);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
