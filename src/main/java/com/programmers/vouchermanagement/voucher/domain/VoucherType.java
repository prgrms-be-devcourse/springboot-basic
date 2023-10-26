package com.programmers.vouchermanagement.voucher.domain;

import com.programmers.vouchermanagement.voucher.exception.VoucherTypeNotFoundException;

import java.util.Arrays;

public enum VoucherType {

    FIXED(new FixedVoucherPolicy()),
    PERCENT(new PercentVoucherPolicy());

    private final VoucherPolicy voucherPolicy;

    VoucherType(VoucherPolicy voucherPolicy) {
        this.voucherPolicy = voucherPolicy;
    }

    public static VoucherType getVoucherTypeByName(String name) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getLowerCaseName().equals(name))
                .findAny().orElseThrow(VoucherTypeNotFoundException::new);
    }

    public String getLowerCaseName() {
        return this.name().toLowerCase();
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }
}
