package com.example.voucher.repository.wallet;

import java.util.UUID;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.example.voucher.domain.wallet.Wallet;
import com.example.voucher.repository.QueryBuilder;

@Component
public class JdbcWalletRepository implements WalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Wallet save(Wallet wallet) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("walletId", wallet.getWalletId().toString())
            .addValue("customerId", wallet.getCustomerId().toString())
            .addValue("voucherId", wallet.getVoucherId().toString());

        String sql = new QueryBuilder().insertInto("WALLET")
            .values("walletId", "customerId", "voucherId")
            .build();

        jdbcTemplate.update(sql, parameterSource);
        UUID walletID = wallet.getWalletId();

        return wallet;
    }
}
