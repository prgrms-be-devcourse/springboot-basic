package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("dev")
@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean saveVoucher(Voucher voucher) {
        String sql = "insert into voucher (voucher_id, discount_amount, voucher_type) values (UUID_TO_BIN(?), ?, ?)";
        jdbcTemplate.update(sql, voucher.getVoucherId().toString().getBytes(), voucher.getDiscountAmount(), voucher.getVoucherType().toString());
        return true;
    }

    @Override
    public Optional<Voucher> getVoucherById(UUID voucherId) {
        String sql = "select * from voucher where voucher_id= UUID_TO_BIN(?)";
        Voucher voucher = jdbcTemplate.queryForObject(sql, new VoucherMapper(), voucherId);
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        String sql = "select * from voucher";
        List<Voucher> list = jdbcTemplate.query(sql, new VoucherMapper());
        return list;
    }
}

class VoucherMapper implements RowMapper<Voucher> {

    public Voucher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Voucher voucher = new Voucher();
        voucher.setVoucherId(UUID.nameUUIDFromBytes(resultSet.getBytes("voucher_id")));
        voucher.setDiscountAmount(resultSet.getDouble("discount_amount"));
        voucher.setVoucherType(VoucherType.valueOf(resultSet.getString("voucher_type")));
        return voucher;
    }
}
