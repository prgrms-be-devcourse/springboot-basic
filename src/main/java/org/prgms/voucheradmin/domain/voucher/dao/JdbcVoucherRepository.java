package org.prgms.voucheradmin.domain.voucher.dao;

import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.CreationFailException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher create(Voucher voucher) {
        int update = jdbcTemplate.update("insert into vouchers(voucher_id, voucher_type, voucher_amount) value(UUID_TO_BIN(?), ?, ?)",
                voucher.getVoucherId().toString().getBytes(), voucher.getVoucherType().name(), voucher.getAmount());

        if(update != 1) {
            throw new CreationFailException();
        }

        return voucher;
    }

    @Override
    public List<Voucher> getAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));

        switch (voucherType) {
            case FIXED_AMOUNT:
                long amount = resultSet.getLong("voucher_amount");
                return new FixedAmountVoucher(voucherId, amount);
            default:
                long percent = resultSet.getLong("voucher_amount");
                return new PercentageDiscountVoucher(voucherId, percent);
        }
    };

    private UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
