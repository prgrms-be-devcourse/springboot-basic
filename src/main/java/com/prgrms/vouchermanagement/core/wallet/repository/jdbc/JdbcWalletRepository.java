package com.prgrms.vouchermanagement.core.wallet.repository.jdbc;

import com.prgrms.vouchermanagement.core.wallet.domain.Wallet;
import com.prgrms.vouchermanagement.core.wallet.repository.WalletRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("prod")
@Repository
public class JdbcWalletRepository implements WalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        String id = resultSet.getString("id");
        String customerId = resultSet.getString("customer_id");
        String voucherId = resultSet.getString("voucher_id");
        return new Wallet(id, customerId, voucherId);
    };

    @Override
    public Wallet save(Wallet wallet) {
        int update = jdbcTemplate.update("INSERT INTO wallets(id, customer_id, voucher_id) VALUES (?, ?, ?)",
                wallet.getId(),
                wallet.getCustomerId(),
                wallet.getVoucherId());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return wallet;
    }

    @Override
    public List<Wallet> findAll() {
        return jdbcTemplate.query("select * from wallets", walletRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallets");
    }

    @Override
    public List<Wallet> findByCustomerId(String customerId) {
        return jdbcTemplate.query("SELECT * FROM wallets WHERE customer_id = ?", walletRowMapper, customerId);
    }

    @Override
    public void deleteAllByCustomerId(String customerId) {
        jdbcTemplate.update("DELETE FROM wallets WHERE customer_id = ?", customerId);
    }

    @Override
    public List<Wallet> findByVoucherId(String voucherId) {
        return jdbcTemplate.query("SELECT * FROM wallets WHERE voucher_id = ?", walletRowMapper, voucherId);
    }

}
