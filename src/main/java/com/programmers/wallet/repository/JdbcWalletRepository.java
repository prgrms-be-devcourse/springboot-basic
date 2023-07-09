package com.programmers.wallet.repository;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcWalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void updateCustomerId(UUID customerId, UUID voucherId) {
        String sql = "update vouchers set customer_id = ? WHERE id = ?";

        jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
    }

    public List<Voucher> findByCustomerId(UUID customerId) {
        String sql = "select * from vouchers where customer_id = ?";

        return jdbcTemplate.query(sql, voucherRowMapper(), customerId.toString());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) ->
                VoucherType.createVoucher(rs.getString("type"),
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getLong("value"));
    }
}
