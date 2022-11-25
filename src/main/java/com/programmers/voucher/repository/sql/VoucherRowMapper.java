package com.programmers.voucher.repository.sql;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.VoucherType;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class VoucherRowMapper implements RowMapper<Voucher> {
    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Voucher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String firstLetterOfType = resultSet.getString("voucher_type").substring(0, 1);
        long value = resultSet.getLong("voucher_value");
        boolean isAssigned = resultSet.getBoolean("assigned");

        VoucherType voucherType = VoucherType.getValidateVoucherType(firstLetterOfType);

        return VoucherFactory.createVoucher(voucherId, voucherType, value, isAssigned);
    }
}
