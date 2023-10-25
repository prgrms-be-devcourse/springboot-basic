package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        String sql = "INSERT INTO vouchers (customer_id, voucher_id, used) VALUES (:customerId, :voucherId, :used)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("customerId", wallet.getCustomerId().toString())
                .addValue("voucherId", wallet.getVoucherId().toString())
                .addValue("used", wallet.isUsed());

        template.update(sql, params);
    }

    @Override
    public List<Wallet> findAll(GetWalletsRequestDto request) {
        String sql = "SELECT * FROM wallets WHERE 1 = 1";

        if (request.getUsed() != null) {
            sql += "AND used = :used";
        }

        if (request.getCustomerId() != null) {
            sql += "AND customer_id = :customerId";
        }

        if (request.getVoucherId() != null) {
            sql += "AND voucher_id = :voucherId";
        }

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("used", request.getUsed())
                .addValue("customerId", request.getCustomerId().toString())
                .addValue("voucherId", request.getVoucherId().toString());

        return template.query(sql, params, getWalletRowMapper());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM vouchers WHERE id = :id";
        template.update(sql, new MapSqlParameterSource("id", id));
    }

    private RowMapper<Wallet> getWalletRowMapper() {
        return (rs, rowNum) -> {
            int id = rs.getInt("id");
            UUID customerId = UUID.fromString(rs.getString("customer_id"));
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
            boolean used = rs.getBoolean("used");

            return new Wallet(id, customerId, voucherId, used);
        };
    }
}
