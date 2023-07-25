package com.wonu606.vouchermanager.service.voucher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType getVoucherTypeByName(String name) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 바우처 타입입니다."));
    }

    public static List<String> getAllNames() {
        return Arrays.stream(VoucherType.values())
                .map(v -> v.name().toLowerCase())
                .collect(Collectors.toList());
    }
}
