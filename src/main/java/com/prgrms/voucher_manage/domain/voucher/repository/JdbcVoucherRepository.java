package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.base.ErrorMessage.*;

@Repository
@RequiredArgsConstructor
@Profile("prod")
public class JdbcVoucherRepository implements VoucherRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher(voucher_id, amount, type) values (?,?,?)";
        jdbcTemplate.update(sql, voucher.getId().toString(), voucher.getDiscountAmount(), voucher.getType().getLabel());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Voucher findById(UUID voucherId) {
        String sql = "select * from voucher where voucher_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, voucherId.toString());
        } catch (Exception e) {
            throw new RuntimeException(VOUCHER_NOT_EXISTS.getMessage());
        }
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "update voucher set amount = ? where voucher_id = ?";
        try {
            jdbcTemplate.update(sql, voucher.getDiscountAmount(), voucher.getId().toString());
        } catch (Exception e){
            throw new RuntimeException(VOUCHER_UPDATE_FAILED.getMessage());
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "delete from voucher where voucher_id = ?";
        try{
            jdbcTemplate.update(sql, voucherId.toString());
        } catch (Exception e){
            throw new RuntimeException(VOUCHER_DELETE_FAILED.getMessage());
        }
    }

    private static final RowMapper<Voucher> rowMapper = (resultSet, i) -> {
        UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
        Long amount = resultSet.getLong("amount");
        String type = resultSet.getString("type");

        return createVoucher(voucherId, amount, type);
    };

    private static Voucher createVoucher(UUID voucherId, Long amount, String type) {
        VoucherType voucherType = VoucherType.matchVoucherType(type);
        if (voucherType == VoucherType.FIXED) {
            return new FixedAmountVoucher(voucherId, amount);
        } else {
            return new PercentDiscountVoucher(voucherId, amount);
        }
    }
}
