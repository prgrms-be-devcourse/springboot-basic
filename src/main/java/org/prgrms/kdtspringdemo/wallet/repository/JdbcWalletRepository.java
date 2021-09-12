package org.prgrms.kdtspringdemo.wallet.repository;

import org.prgrms.kdtspringdemo.customer.Customer;
import org.prgrms.kdtspringdemo.wallet.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Profile("dev")
@Repository
public class JdbcWalletRepository implements WalletRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String VERSION_8_0_UUID = "UUID_TO_BIN(:customerId)";
    private final String VERSION_5_7_UUID = "UNHEX(REPLACE(:customerId, '-', ''))";
    private final String CURRENT_UUID = VERSION_5_7_UUID;
    private String getVersion57UUID(String value) {
        return "UNHEX(REPLACE(:" + value + ", '-', ''))";
    }

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toparamMap(Wallet wallet) {
        return new HashMap<String, Object>() {{
            put("walletId", wallet.getWalletId().toString().getBytes());
            put("customerId", wallet.getCustomerId().toString().getBytes());
            put("createdAt", Timestamp.valueOf(wallet.getCreatedAt().truncatedTo(ChronoUnit.MILLIS)));
        }};
    }

    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update("INSERT INTO wallets(wallet_id, customer_id, created_at) VALUES (" + getVersion57UUID("walletId") + ", " + getVersion57UUID("customerId")+", :createdAt)",
                toparamMap(wallet));
        System.out.println(jdbcTemplate.toString());
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
}
