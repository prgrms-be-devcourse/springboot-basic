package com.programmers.vouchermanagement.util;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class DomainMapper {
    public final RowMapper<Ownership> ownershipRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        return new Ownership(voucherId, customerId);
    };
    public final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        long discountValue = resultSet.getLong("discount_value");
        String voucherTypeStr = resultSet.getString("type");

        return new Voucher(id, discountValue, VoucherType.valueOf(voucherTypeStr));
    };
    public final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        boolean isBlack = resultSet.getBoolean("black");

        return new Customer(id, name, isBlack);
    };
    public final RowMapper<UUID> uuidRowMapper = (resultSet, i) ->
            toUUID(resultSet.getBytes("id"));

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public Map<String, Object> customerToParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("black", customer.isBlack());
        return paramMap;
    }

    public Map<String, Object> ownershipToParamMap(Ownership ownership) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", ownership.voucherId().toString().getBytes());
        paramMap.put("customerId", ownership.customerId().toString().getBytes());
        return paramMap;
    }

    public Map<String, Object> uuidToParamMap(UUID id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id.toString().getBytes());
        return paramMap;
    }

    public Map<String, Object> voucherToParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", voucher.getVoucherId().toString().getBytes());
        paramMap.put("type", voucher.getVoucherType().name());
        paramMap.put("discountValue", voucher.getDiscountValue());
        return paramMap;
    }
}
