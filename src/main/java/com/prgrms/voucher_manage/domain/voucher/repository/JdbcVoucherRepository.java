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

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Profile("prod")
public class JdbcVoucherRepository implements VoucherRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher(voucher_id, amount, type) values (UUID_TO_BIN(?),?,?)";
        jdbcTemplate.update(sql,voucher.getId().toString().getBytes(), voucher.getDiscountAmount(), voucher.getType().getLabel());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        return jdbcTemplate.query(sql, rowMapper);
    }

    private static final RowMapper<Voucher> rowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        Long amount = resultSet.getLong("amount");
        String type = resultSet.getString("type");

        VoucherType voucherType = VoucherType.matchVoucherType(type);
        if (voucherType==VoucherType.FIXED){
            return new FixedAmountVoucher(voucherId, amount);
        } else {
            return new PercentDiscountVoucher(voucherId, amount);
        }
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
