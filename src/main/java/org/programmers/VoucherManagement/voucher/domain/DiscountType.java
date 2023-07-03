package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_EXIST_DISCOUNT_TYPE;

public enum DiscountType {
    PERCENT("percent", "%"),
    FIXED("fixed", "₩");

    private final String type;
    private final String symbol;

    private static final Map<String, DiscountType> DISCOUNT_TYPE_MAP = Collections
            .unmodifiableMap(Arrays
                    .stream(values())
                    .collect(Collectors.toMap(DiscountType::getType, Function.identity())));

    DiscountType(String type, String symbol) {
        this.type = type;
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public static DiscountType from(String key) {
        try {
            if (DISCOUNT_TYPE_MAP.containsKey(key)) {
                return DISCOUNT_TYPE_MAP.get(key);
            }
            throw new VoucherException(NOT_EXIST_DISCOUNT_TYPE);
        } catch (VoucherException e) {
            throw new VoucherException(NOT_EXIST_DISCOUNT_TYPE, e);
        }
    }
}
