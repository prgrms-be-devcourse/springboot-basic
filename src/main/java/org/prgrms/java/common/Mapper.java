package org.prgrms.java.common;

import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Mapper {
    private Mapper() {
    }

    public static Voucher mapToVoucher(String object) {
        String[] atoms = object.split(",");
        UUID voucherId = UUID.fromString(atoms[0].trim());
        String amount = atoms[1].trim();
        String type = atoms[2].trim();
        LocalDateTime expiredAt = LocalDateTime.parse(atoms[3].trim());
        boolean used = Boolean.parseBoolean(atoms[3].trim());

        return mapToVoucher(type, voucherId, Long.parseLong(amount), expiredAt, used);
    }

    public static Voucher mapToVoucher(String type, UUID voucherId, long amount, LocalDateTime expiredAt, boolean used) {
        switch (type) {
            case "PERCENT":
                return new PercentDiscountVoucher(voucherId, amount, used, expiredAt);
            case "FIXED":
                return new FixedAmountVoucher(voucherId, amount, used, expiredAt);
            default:
                throw new VoucherException("Unknown voucher type.");
        }
    }

    public static Customer mapToCustomer(String object) {
        String[] atoms = object.split(",");
        UUID customerId = UUID.fromString(atoms[0].trim());
        String name = atoms[1].trim();
        String email = atoms[2].trim();
        boolean isBlocked = Boolean.parseBoolean(atoms[3].trim());

        return new Customer(customerId, name, email, isBlocked);
    }


    public static final RowMapper<Voucher> mapToVoucher = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long amount = resultSet.getLong("amount");
        String type = resultSet.getString("type");
        LocalDateTime expiredAt = resultSet.getTimestamp("expired_at").toLocalDateTime();
        boolean used = resultSet.getBoolean("used");

        return Mapper.mapToVoucher(type, voucherId, amount, expiredAt, used);
    };

    public static final RowMapper<Customer> mapToCustomer = (resultSet, rowNum) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        boolean isBlocked = resultSet.getBoolean("is_blocked");
        return new Customer(customerId, customerName, email, isBlocked);
    };

    public static Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("amount", voucher.getAmount());
            put("type", voucher.getType());
            put("expiredAt", voucher.getExpiredAt());
            put("used", voucher.isUsed());
        }};
    }

    public static Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("isBlocked", customer.isBlocked());
        }};
    }

    public static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
