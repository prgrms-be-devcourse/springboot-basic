package com.example.voucher.repository.wallet;

import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.example.voucher.domain.wallet.Wallet;
import com.example.voucher.repository.QueryBuilder;
import com.example.voucher.util.FormatConvertor;

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

        return wallet;
    }

    @Override
    public List<Wallet> findByConditionId(String condition, UUID conditionID) {
        String fieldName = FormatConvertor.convertToCamelCase(condition);
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue(fieldName, conditionID.toString());

        RowMapper<Wallet> walletRowMapper = walletRowMapper();

        String sql = new QueryBuilder().select("*")
            .from("WALLET")
            .where(condition, "=", fieldName)
            .build();

        return jdbcTemplate.query(sql, parameterSource, walletRowMapper);
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
