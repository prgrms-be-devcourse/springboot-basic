package com.example.voucher_manager.domain.voucher;

import com.example.voucher_manager.io.CommandType;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent"),
    ERROR("error");

    private final String type;
    private static final Map<String, VoucherType> TYPE_OF_VOUCHER = Arrays.stream(VoucherType.values())
            .collect(Collectors.toMap(VoucherType::getType, Function.identity()));

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType of(String type) {
        return TYPE_OF_VOUCHER.get(type);
    }

    public static boolean isValidated(String type){
        return TYPE_OF_VOUCHER.containsKey(type);
    }

    public String getType() {
        return type;
    }
}
