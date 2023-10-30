package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.*;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcTemplateWalletRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public JdbcTemplateWalletRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Wallet wallet) {
        String sql = "INSERT INTO wallets (customer_id, voucher_id, used) VALUES (:customerId, :voucherId, :used)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("customerId", wallet.getCustomer().getId().toString())
                .addValue("voucherId", wallet.getVoucher().getId().toString())
                .addValue("used", wallet.isUsed());

        template.update(sql, params);
    }

    @Override
    public void saveAll(List<Wallet> wallets) {
        String sql = "INSERT INTO wallets (customer_id, voucher_id, used) VALUES (:customerId, :voucherId, :used)";

        template.batchUpdate(sql, wallets.stream()
                .map(wallet -> new MapSqlParameterSource()
                        .addValue("customerId", wallet.getCustomer().getId().toString())
                        .addValue("voucherId", wallet.getVoucher().getId().toString())
                        .addValue("used", wallet.isUsed()))
                .toArray(SqlParameterSource[]::new));
    }

    @Override
    public Optional<Wallet> findById(int id) {
        String sql = """
                SELECT *
                FROM wallets
                         LEFT JOIN customers ON wallets.customer_id = customers.id
                         LEFT JOIN vouchers ON wallets.voucher_id = vouchers.id
                WHERE wallets.id = :id\s
                """;

        SqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            return Optional.ofNullable(template.queryForObject(sql, params, getWalletRowMapper()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findAll(GetWalletsRequestDto request) {
        String sql = """
                SELECT *
                FROM wallets
                         LEFT JOIN customers ON wallets.customer_id = customers.id
                         LEFT JOIN vouchers ON wallets.voucher_id = vouchers.id
                WHERE 1 = 1\s
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (request.getUsed() != null) {
            sql += "AND used = :used";
            params.addValue("used", request.getUsed());
        }

        if (request.getCustomerId() != null) {
            sql += "AND customer_id = :customerId";
            params.addValue("customerId", request.getCustomerId().toString());
        }

        if (request.getVoucherId() != null) {
            sql += "AND voucher_id = :voucherId";
            params.addValue("voucherId", request.getVoucherId().toString());
        }

        return template.query(sql, params, getWalletRowMapper());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM wallets WHERE id = :id";
        template.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM wallets";
        template.update(sql, new MapSqlParameterSource());
    }

    private RowMapper<Wallet> getWalletRowMapper() {
        return (rs, rowNum) -> {
            int id = rs.getInt("id");
            boolean used = rs.getBoolean("used");

            UUID customerId = UUID.fromString(rs.getString("customer_id"));
            String email = rs.getString("email");
            boolean blacklisted = rs.getBoolean("blacklisted");
            Customer customer = new Customer(customerId, email, blacklisted);

            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
            VoucherType type = VoucherType.valueOf(rs.getString("type"));
            Long amount = rs.getLong("amount");
            Voucher voucher = VoucherFactory.create(voucherId, amount, type);

            return new Wallet(id, customer, voucher, used);
        };
    }
}
