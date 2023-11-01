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
    public static final String ID_KEY = "id";
    public static final String VOUCHER_ID_KEY = "voucher_id";
    public static final String CUSTOMER_ID_KEY = "customer_id";
    public static final String BLACK_KEY = "black";
    public static final String NAME_KEY = "name";
    public static final String DISCOUNT_VALUE_KEY = "discount_value";
    public static final String TYPE_KEY = "type";

    public final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(ID_KEY));
        long discountValue = resultSet.getLong(DISCOUNT_VALUE_KEY);
        String voucherTypeStr = resultSet.getString(TYPE_KEY);

        return new Voucher(id, discountValue, VoucherType.valueOf(voucherTypeStr));
    };
    public final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(ID_KEY));
        String name = resultSet.getString(NAME_KEY);
        boolean isBlack = resultSet.getBoolean(BLACK_KEY);

        return new Customer(id, name, isBlack);
    };

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public Map<String, Object> customerToParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, customer.getCustomerId().toString().getBytes());
        paramMap.put(NAME_KEY, customer.getName());
        paramMap.put(BLACK_KEY, customer.isBlack());
        return paramMap;
    }

    public Map<String, Object> ownershipToParamMap(Ownership ownership) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(VOUCHER_ID_KEY, ownership.voucherId().toString().getBytes());
        paramMap.put(CUSTOMER_ID_KEY, ownership.customerId().toString().getBytes());
        return paramMap;
    }

    public Map<String, Object> uuidToParamMap(UUID id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, id.toString().getBytes());
        return paramMap;
    }

    public Map<String, Object> voucherToParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, voucher.voucherId().toString().getBytes());
        paramMap.put(TYPE_KEY, voucher.voucherType().name());
        paramMap.put(DISCOUNT_VALUE_KEY, voucher.discountValue());
        return paramMap;
    }
}
