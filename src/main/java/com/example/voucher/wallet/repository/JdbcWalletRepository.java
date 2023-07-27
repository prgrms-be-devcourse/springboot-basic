package com.example.voucher.wallet.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.example.voucher.util.QueryBuilder;
import com.example.voucher.wallet.model.Wallet;

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

        return findById(wallet.getWalletId());
    }

    @Override
    public Wallet findById(UUID walletID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("walletId", walletID.toString());

        RowMapper<Wallet> walletRowMapper = walletRowMapper();

        String sql = new QueryBuilder().select("*")
            .from("WALLET")
            .where("WALLET_ID", "=", "walletId")
            .build();

        return jdbcTemplate.queryForObject(sql, parameterSource, walletRowMapper);
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("customerId", customerId.toString());

        RowMapper<Wallet> walletRowMapper = walletRowMapper();

        String sql = new QueryBuilder().select("*")
            .from("WALLET")
            .where("CUSTOMER_ID", "=", "customerId")
            .build();

        return jdbcTemplate.query(sql, parameterSource, walletRowMapper);
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("voucherId", voucherId.toString());

        RowMapper<Wallet> walletRowMapper = walletRowMapper();

        String sql = new QueryBuilder().select("*")
            .from("WALLET")
            .where("VOUCHER_ID", "=", "voucherId")
            .build();

        return jdbcTemplate.query(sql, parameterSource, walletRowMapper);
    }

    @Override
    public void deleteById(UUID customerId, UUID voucherId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("customerId", customerId.toString())
            .addValue("voucherId", voucherId.toString());

        String sql = new QueryBuilder().delete("WALLET")
            .where("CUSTOMER_ID", "=", "customerId")
            .and("VOUCHER_ID", "=", "voucherId")
            .build();

        jdbcTemplate.update(sql, parameterSource);
    }

    private RowMapper<Wallet> walletRowMapper() {
        return (rs, rowNum) -> {
            UUID walletId = UUID.fromString(rs.getString("wallet_id"));
            UUID customerId = UUID.fromString(rs.getString("customer_id"));
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));

            return new Wallet(walletId, customerId, voucherId);
        };
    }
}
