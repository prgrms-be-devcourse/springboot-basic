package com.wonu606.vouchermanager.service;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static Optional<VoucherType> getVoucherTypeByName(String name) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(name.toUpperCase()))
                .findFirst();
    }
}
