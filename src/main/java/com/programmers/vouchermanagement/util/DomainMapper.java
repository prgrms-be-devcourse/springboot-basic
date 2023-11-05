package com.programmers.vouchermanagement.util;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.wallet.domain.Ownership;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DomainMapper {
    //TODO: split
    public static final String ID_KEY = "id";
    public static final String VOUCHER_ID_KEY = "voucher_id";
    public static final String CUSTOMER_ID_KEY = "customer_id";
    public static final String BLACK_KEY = "black";
    public static final String NAME_KEY = "name";
    public static final String DISCOUNT_VALUE_KEY = "discount_value";
    public static final String TYPE_KEY = "type";
    public static final String CREATED_AT_KEY = "created_at";
    public static final String FROM_KEY = "from";
    public static final String TO_KEY = "to";
    public static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(ID_KEY));
        long discountValue = resultSet.getLong(DISCOUNT_VALUE_KEY);
        String voucherTypeStr = resultSet.getString(TYPE_KEY);
        LocalDateTime createdAt = resultSet.getTimestamp(CREATED_AT_KEY).toLocalDateTime();

        return new Voucher(id, createdAt, voucherTypeStr, discountValue);
    };
    public static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(ID_KEY));
        String name = resultSet.getString(NAME_KEY);
        boolean isBlack = resultSet.getBoolean(BLACK_KEY);

        return new Customer(id, name, isBlack);
    };

    private DomainMapper() {
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static Map<String, Object> customerToParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, customer.getId().toString().getBytes());
        paramMap.put(NAME_KEY, customer.getName());
        paramMap.put(BLACK_KEY, customer.isBlack());
        return paramMap;
    }

    public static Map<String, Object> ownershipToParamMap(Ownership ownership) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(VOUCHER_ID_KEY, ownership.voucherId().toString().getBytes());
        paramMap.put(CUSTOMER_ID_KEY, ownership.customerId().toString().getBytes());
        return paramMap;
    }

    public static Map<String, Object> uuidToParamMap(UUID id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, id.toString().getBytes());
        return paramMap;
    }

    public static Map<String, Object> voucherToParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, voucher.getId().toString().getBytes());
        paramMap.put(CREATED_AT_KEY, Timestamp.valueOf(voucher.getCreatedAt()));
        paramMap.put(TYPE_KEY, voucher.getTypeName());
        paramMap.put(DISCOUNT_VALUE_KEY, voucher.getDiscountValue());
        return paramMap;
    }
}
