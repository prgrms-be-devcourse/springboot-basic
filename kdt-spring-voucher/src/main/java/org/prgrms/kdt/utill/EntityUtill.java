package org.prgrms.kdt.utill;

import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.CustomerVoucherEntity;
import org.prgrms.kdt.domain.VoucherEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;


@Component
public class EntityUtill {

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static HashMap<String, Object> toCustomerParamMap(CustomerEntity customer) {
        return new HashMap<>() {
            {
                put("customerId", customer.getCustomerId().toString().getBytes());
                put("name", customer.getName());
                put("email", customer.getEmail());
                put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
                put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            }
        };
    }

    public static RowMapper<CustomerEntity> customerEntityRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ? resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new CustomerEntity(customerId, customerName, email, lastLoginAt, createdAt);
    };

    public static HashMap<String, Object> toParamMap(CustomerVoucherEntity customerVoucherEntity) {
        return new HashMap<>() {
            {
                put("customerVoucherId", customerVoucherEntity.getCustomerVoucherId().toString().getBytes());
                put("customerId", customerVoucherEntity.getCustomerId().toString().getBytes());
                put("voucherId", customerVoucherEntity.getVoucherId().toString().getBytes());
                put("createdAt", Timestamp.valueOf(customerVoucherEntity.getCreatedAt()));
            }
        };
    }

    public static RowMapper<CustomerVoucherEntity> customerVoucherEntityRowMapper = (resultSet, i) -> {
        var customerVoucherId = toUUID(resultSet.getBytes("customer_voucher_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new CustomerVoucherEntity(customerVoucherId, customerId, voucherId, createdAt);
    };

    public static HashMap<String, Object> toVoucherParamMap(VoucherEntity voucherEntity) {
        return new HashMap<>() {
            {
                put("voucherId", voucherEntity.getVoucherId().toString().getBytes());
                put("voucherType", voucherEntity.getVoucherType());
                put("discount", voucherEntity.getDiscount());
                put("createdAt", Timestamp.valueOf(voucherEntity.getCreatedAt()));
            }
        };
    }

    public static RowMapper<VoucherEntity> voucherEntityRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = resultSet.getString("voucher_type");
        var discount = resultSet.getLong("discount");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new VoucherEntity(voucherId, voucherType, discount, createdAt);
    };




}
