package org.prgms.voucherProgram.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.exception.NothingChangeException;
import org.springframework.jdbc.core.RowMapper;

public class DatabaseUtils {

    public static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        var createdTime = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLoginTime = resultSet.getTimestamp("last_login_at") != null ?
            resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, name, email, createdTime, lastLoginTime);
    };

    private static final int WRONG_EXCUTE_VALUE = 0;

    private DatabaseUtils() {
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static byte[] toBytes(UUID customerId) {
        return customerId.toString().getBytes();
    }

    public static void validateExecute(int excute) {
        if (excute == WRONG_EXCUTE_VALUE) {
            throw new NothingChangeException();
        }
    }
}
