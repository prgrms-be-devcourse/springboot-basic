package com.example.voucher.wallet.repository;

import static java.util.Map.*;
import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.example.voucher.query.Delete;
import com.example.voucher.query.Insert;
import com.example.voucher.query.Select;
import com.example.voucher.query.Where;
import com.example.voucher.query.operator.Eq;
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

        Insert insert = Insert.into(Wallet.class)
            .values(of(
                "WALLET_ID", ":walletId",
                "CUSTOMER_ID", ":customerId",
                "VOUCHER_ID", ":voucherId"
            ));

        jdbcTemplate.update(insert.getQuery(), parameterSource);

        return findById(wallet.getWalletId());
    }

    @Override
    public Wallet findById(UUID walletID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("walletId", walletID.toString());

        RowMapper<Wallet> walletRowMapper = walletRowMapper();

        Where where = Where.builder()
            .where(new Eq("WALLET_ID", ":walletId"))
            .build();

        Select select = Select.builder()
            .select("*")
            .from(Wallet.class)
            .where(where)
            .build();

        return jdbcTemplate.queryForObject(select.getQuery(), parameterSource, walletRowMapper);
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("customerId", customerId.toString());

        RowMapper<Wallet> walletRowMapper = walletRowMapper();

        Where where = Where.builder()
            .where(new Eq("CUSTOMER_ID", ":customerId"))
            .build();

        Select select = Select.builder()
            .select("*")
            .from(Wallet.class)
            .where(where)
            .build();

        return jdbcTemplate.query(select.getQuery(), parameterSource, walletRowMapper);
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("voucherId", voucherId.toString());

        RowMapper<Wallet> walletRowMapper = walletRowMapper();

        Where where = Where.builder()
            .where(new Eq("VOUCHER_ID", ":voucherId"))
            .build();

        Select select = Select.builder()
            .select("*")
            .from(Wallet.class)
            .where(where)
            .build();

        return jdbcTemplate.query(select.getQuery(), parameterSource, walletRowMapper);
    }

    @Override
    public void deleteById(UUID customerId, UUID voucherId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("customerId", customerId.toString())
            .addValue("voucherId", voucherId.toString());

        Where where = Where.builder()
            .where(new Eq("CUSTOMER_ID", ":customerId"))
            .and(new Eq("VOUCHER_ID", ":voucherId"))
            .build();

        Delete delete = Delete.builder()
            .delete(Wallet.class)
            .where(where)
            .build();

        jdbcTemplate.update(delete.getQuery(), parameterSource);
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
