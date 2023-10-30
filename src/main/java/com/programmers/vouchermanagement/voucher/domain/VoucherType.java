package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.VoucherTypeNotFoundException;

import java.util.Arrays;

public enum VoucherType {

    FIXED,
    PERCENT;

    public static VoucherType getVoucherTypeByName(String name) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getName().equalsIgnoreCase(name))
                .findAny().orElseThrow(VoucherTypeNotFoundException::new);
    }

    private String getName() {
        return this.name();
    }
}
