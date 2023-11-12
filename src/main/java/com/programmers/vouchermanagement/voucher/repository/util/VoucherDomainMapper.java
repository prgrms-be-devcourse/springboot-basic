package com.programmers.vouchermanagement.voucher.repository.util;

import com.programmers.vouchermanagement.util.DomainMapper;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VoucherDomainMapper extends DomainMapper {
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

    private VoucherDomainMapper() {
    }

    public static Map<String, Object> voucherToParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ID_KEY, voucher.getId().toString().getBytes());
        paramMap.put(CREATED_AT_KEY, Timestamp.valueOf(voucher.getCreatedAt()));
        paramMap.put(TYPE_KEY, voucher.getTypeName());
        paramMap.put(DISCOUNT_VALUE_KEY, voucher.getDiscountValue());
        return paramMap;
    }


    public static Voucher objectToVoucher(Map<String, String> voucherObject) {
        UUID voucherId = UUID.fromString(String.valueOf(voucherObject.get(ID_KEY)));
        LocalDateTime createdAt = LocalDateTime.parse(voucherObject.get(CREATED_AT_KEY));
        String voucherTypeName = String.valueOf(voucherObject.get(TYPE_KEY));
        long discountValue = Long.parseLong(String.valueOf(voucherObject.get(DISCOUNT_VALUE_KEY)));
        //TODO: check save format
        return new Voucher(voucherId, createdAt, voucherTypeName, discountValue);
    }

    public static HashMap<String, Object> voucherToObject(Voucher voucher) {
        HashMap<String, Object> voucherObject = new HashMap<>();
        voucherObject.put(ID_KEY, voucher.getId().toString());
        voucherObject.put(CREATED_AT_KEY, voucher.getCreatedAt().toString());
        voucherObject.put(TYPE_KEY, voucher.getTypeName());
        voucherObject.put(DISCOUNT_VALUE_KEY, voucher.getDiscountValue());
        return voucherObject;
    }
}
