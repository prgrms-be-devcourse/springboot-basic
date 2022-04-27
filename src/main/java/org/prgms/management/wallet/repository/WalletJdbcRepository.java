package org.prgms.management.wallet.repository;

import org.prgms.management.wallet.entity.Wallet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

public class WalletJdbcRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WalletJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Wallet> rowMapper = (resultSet, i) -> {
        var walletId = toUUID(resultSet.getBytes("wallet_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var vouchers = resultSet.getObject("vouchers", ArrayList.class);
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Wallet(walletId, customerId, vouchers, createdAt);
    };

    @Override
    public Optional<Wallet> insert(Wallet wallet) {
        var executeUpdate = jdbcTemplate.update("INSERT INTO wallets(" +
                        "wallet_id, customer_id, voucher_id, created_at) " +
                        "VALUES (UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId), " +
                        ":type, :discountNum, :createdAt)",
                toParamMap(wallet));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(wallet);
    }

    @Override
    public List<Wallet> findAll() {
        return jdbcTemplate.query("SELECT * FROM wallets", rowMapper);
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM wallets WHERE wallet_id = :walletId",
                Collections.singletonMap("walletId", walletId),
                rowMapper));
    }

    @Override
    public Optional<Wallet> findByCustomerId(UUID customerId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM wallets WHERE customer_id = :customerId",
                Collections.singletonMap("customerId", customerId),
                rowMapper));
    }

    @Override
    public Optional<Wallet> delete(Wallet wallet) {
        var executeUpdate = jdbcTemplate.update(
                "DELETE FROM wallets WHERE wallet_id = UUID_TO_BIN(:walletId)",
                toParamMap(wallet));

        if (executeUpdate != 1) {
            return Optional.empty();
        }

        return Optional.of(wallet);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallets", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Wallet wallet) {
        Map<String, Object> map = new HashMap<>();
        map.put("walletId", wallet.getWalletId().toString().getBytes());
        map.put("customerId", wallet.getCustomerId().toString().getBytes());
        map.put("vouchers", wallet.getVouchers());
        map.put("createdAt", Timestamp.valueOf(wallet.getCreatedAt()));
        return map;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
