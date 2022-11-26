package com.programmers.voucher.utils;

import com.programmers.voucher.controller.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.model.voucher.Voucher;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;
import java.util.UUID;

public class JdbcParamMapper {
    public static SqlParameterSource toCustomerMap(CustomerCreateRequest customerCreateRequest) {
        return new MapSqlParameterSource()
                .addValue("customerName", customerCreateRequest.customerName())
                .addValue("email", customerCreateRequest.email());
    }

    public static SqlParameterSource toEmailMap(String email) {
        return new MapSqlParameterSource()
                .addValue("email", email);
    }

    public static Map<String, Object> toVoucherIdMap(UUID voucherId) {
        return Map.of(
                "voucherId", voucherId.toString().getBytes()
        );
    }

    public static Map<String, Object> toVoucherMap(Voucher voucher) {
        return Map.of(
                "voucherId", voucher.getVoucherId().toString().getBytes(),
                "discountValue", voucher.getDiscountValue(),
                "voucherName", voucher.getClass().getSimpleName()
        );
    }

    public static Map<String, Object> toAssignMap(Voucher voucher) {
        return Map.of(
                "voucherId", voucher.getVoucherId().toString().getBytes(),
                "customerId", voucher.getCustomer().getCustomerId()
        );
    }
}
