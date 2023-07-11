package me.kimihiqq.vouchermanagement.domain.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Profile({"db", "test"})
@Repository
public class JDBCVoucherRepository implements VoucherRepository {
    private final JdbcTemplate jdbcTemplate;

    public JDBCVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> {
        UUID id = UUID.fromString(rs.getString("id"));
        String type = rs.getString("type");
        int discount = rs.getInt("discount");

        if(type.equals(VoucherTypeOption.FIXED.name())) {
            return new FixedAmountVoucher(id, discount);
        } else {
            return new PercentDiscountVoucher(id, discount);
        }
    };

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "INSERT INTO vouchers(id, type, discount) values (?, ?, ?)";
        jdbcTemplate.update(sql, voucher.getVoucherId().toString(), voucher.getType(), voucher.getDiscount());
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "SELECT * FROM vouchers WHERE id = ?";
        List<Voucher> vouchers = jdbcTemplate.query(sql, voucherRowMapper, voucherId.toString());
        return vouchers.isEmpty() ? Optional.empty() : Optional.of(vouchers.get(0));
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM vouchers";
        return jdbcTemplate.query(sql, voucherRowMapper);
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "DELETE FROM vouchers WHERE id = ?";
        jdbcTemplate.update(sql, voucherId.toString());
    }

    @Override
    public List<Voucher> findAllByCreationDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT * FROM vouchers WHERE creationDateTime BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, voucherRowMapper, Timestamp.valueOf(start), Timestamp.valueOf(end));
    }

    @Override
    public List<Voucher> findAllByType(VoucherTypeOption type) {
        String sql = "SELECT * FROM vouchers WHERE type = ?";
        return jdbcTemplate.query(sql, voucherRowMapper, type.name());
    }


}
