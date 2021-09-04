package com.example.kdtspringmission.voucher.repository;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVoucherRepository implements VoucherRepository{

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = new RowMapper<Voucher>() {
        @Override
        public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException {
            UUID voucherId = toUUID(rs.getBytes("voucher_id"));
            String voucherType = rs.getString("voucher_type");
            long discountAmount = rs.getLong("discount_amount");
            UUID customerId = rs.getBytes("customer_id") == null ? null : toUUID(rs.getBytes("customer_id"));

            if (voucherType.equals("FixedAmountVoucher")) {
                return new FixedAmountVoucher(voucherId, discountAmount, customerId);
            }

            if (voucherType.equals("RateAmountVoucher")) {
                return new RateAmountVoucher(voucherId, discountAmount, customerId);
            }

            throw new IllegalArgumentException(
                "None Matched Existing Voucher Type, voucherType" + voucherType);
        }
    };

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID insert(Voucher voucher) {
        int update = jdbcTemplate.update(
            "insert into vouchers(voucher_id, voucher_type, discount_amount, customer_id) VALUES (uuid_to_bin(?), ?, ?, uuid_to_bin(?))",
            voucher.getId().toString().getBytes(),
            voucher.getTypeName(),
            voucher.getDiscountAmount(),
            voucher.getOwnerId() == null ? null : voucher.getOwnerId().toString().getBytes()
        );

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return voucher.getId();
    }

    @Override
    public Voucher findById(UUID id) {
        return jdbcTemplate.queryForObject(
            "select * from vouchers where voucher_id = uuid_to_bin(?)",
            voucherRowMapper, id.toString().getBytes());
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(
            "update vouchers set discount_amount = ?, customer_id = uuid_to_bin(?)  where voucher_id = uuid_to_bin(?)",
            voucher.getDiscountAmount(),
            voucher.getOwnerId() == null ? null : voucher.getOwnerId().toString().getBytes(),
            voucher.getId().toString().getBytes()
        );

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }


    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public List<Voucher> findByOwnerId(UUID ownerId) {
        return jdbcTemplate.query(
            "select * from vouchers where customer_id = uuid_to_bin(?)",
            voucherRowMapper, ownerId.toString().getBytes()
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers");
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
