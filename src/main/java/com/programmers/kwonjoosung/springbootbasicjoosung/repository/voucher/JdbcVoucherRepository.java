package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("release")
public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        final String sql = "INSERT INTO vouchers(voucher_id, voucher_type, discount) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                voucher.getVoucherId().toString(),
                voucher.getVoucherType().name(),
                voucher.getDiscount());
        return voucher;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        final String sql = "SELECT * FROM vouchers WHERE voucher_id = ?";
        return jdbcTemplate.queryForObject(sql, voucherRowMapper, voucherId.toString());
    }

    @Override
    public List<Voucher> findAll() {
        final String sql = "SELECT * FROM vouchers";
        return jdbcTemplate.query(sql, voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        final String sql = "UPDATE vouchers SET voucher_type = ?, discount = ? WHERE voucher_id = ?";
        jdbcTemplate.update(sql,
                voucher.getVoucherType().name(),
                voucher.getDiscount(),
                voucher.getVoucherId().toString());
        return voucher;
    }

    @Override
    public int deleteById(UUID voucherId) {
        final String sql = "DELETE FROM vouchers WHERE voucher_id = ?";
        return jdbcTemplate.update(sql, voucherId.toString());
    }

    private final RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> {
        return VoucherFactory.createVoucher(
                VoucherType.getVoucherType(rs.getString("voucher_type")),
                UUID.fromString(rs.getString("voucher_id")),
                rs.getLong("discount"));
    };

//    public int deleteAll() {
//        final String sql = "DELETE FROM vouchers";
//        return jdbcTemplate.update(sql);
//    }
}
