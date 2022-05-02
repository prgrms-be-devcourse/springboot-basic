package org.prgms.voucherProgram.global.utils;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.domain.Customer;
import org.prgms.voucherProgram.domain.voucher.domain.Voucher;
import org.prgms.voucherProgram.domain.voucher.domain.VoucherType;
import org.prgms.voucherProgram.global.error.exception.NothingChangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class DatabaseUtils {
    public static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdTime = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginTime = resultSet.getTimestamp("last_login_at") != null ?
            resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, name, email, createdTime, lastLoginTime);
    };

    public static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        int voucherType = resultSet.getInt("type");
        Long discountValue = resultSet.getLong("discount");
        LocalDateTime createdTime = resultSet.getTimestamp("created_at").toLocalDateTime();
        return VoucherType.findByNumber(voucherType).constructor(voucherId, customerId, discountValue, createdTime);
    };

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);
    private static final int WRONG_EXCUTE_VALUE = 0;

    private DatabaseUtils() {
    }

    private static UUID toUUID(byte[] bytes) {
        if (Objects.isNull(bytes)) {
            return null;
        }
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static byte[] toBytes(UUID customerId) {
        if (Objects.isNull(customerId)) {
            return null;
        }
        return customerId.toString().getBytes();
    }

    public static void validateExecute(int excute) {
        if (excute == WRONG_EXCUTE_VALUE) {
            logger.error("SQL excute but nothing change");
            throw new NothingChangeException();
        }
    }
}
