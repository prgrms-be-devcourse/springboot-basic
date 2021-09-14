package org.prgrms.kdtspringdemo.wallet.repository;

import org.prgrms.kdtspringdemo.wallet.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Profile("dev")
@Repository
public class JdbcWalletRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private String getVersion57UUID(String value) {
        return "UNHEX(REPLACE(:" + value + ", '-', ''))";
    }

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        var walletId = toUUID(resultSet.getBytes("wallet_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Wallet(walletId, customerId, voucherId, createdAt);
    };

    private Map<String, Object> toparamMap(Wallet wallet) {
        return new HashMap<String, Object>() {{
            put("walletId", wallet.getWalletId().toString().getBytes());
            put("customerId", wallet.getCustomerId().toString().getBytes());
            put("voucherId", wallet.getVoucherId().toString().getBytes());
            put("createdAt", Timestamp.valueOf(wallet.getCreatedAt().truncatedTo(ChronoUnit.MILLIS)));
        }};
    }

    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update("INSERT INTO wallets(wallet_id, customer_id, voucher_id, created_at)" +
                        " VALUES (" + getVersion57UUID("walletId") + ", "
                        + getVersion57UUID("customerId") + ", "
                        + getVersion57UUID("voucherId") + ", :createdAt)",
                toparamMap(wallet));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return wallet;
    }

    @Override
    public Wallet update(Wallet customer) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Wallet> findAll() {
        return null;
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        return Optional.empty();
    }

    @Override
    public Optional<Wallet> findByCustomerId(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteByVoucherId(String voucherId) {
        jdbcTemplate.update("DELETE FROM wallets WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                Collections.singletonMap("voucherId", voucherId.getBytes()));
    }
}
