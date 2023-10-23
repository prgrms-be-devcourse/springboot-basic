package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.util.UUIDUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository{

    private static final String INSERT_QUERY = "INSERT INTO vouchers(voucher_id, discount_type, discount_value) VALUES(UUID_TO_BIN(?), ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM vouchers";

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = UUIDUtils.toUUID(resultSet.getBytes("voucher_id"));
        String discountType = resultSet.getString("discount_type");
        long discountValue = resultSet.getLong("discount_value");
        return switch (DiscountType.find(discountType)) {
            case FIXED -> FixedAmountVoucher.create(voucherId, discountType, discountValue);
            case PERCENT -> PercentDiscountVoucher.create(voucherId, discountType, discountValue);
        };
    };

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        jdbcTemplate.update(INSERT_QUERY,
                voucher.getVoucherId().toString().getBytes(),
                voucher.getDiscountType().toString(),
                voucher.getDiscountValue());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL, voucherRowMapper);
    }
}
