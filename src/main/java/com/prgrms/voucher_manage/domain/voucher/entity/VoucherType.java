package com.prgrms.voucher_manage.domain.voucher.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String label;

    VoucherType(String label) {
        this.label = label;
    }

    public static VoucherType matchVoucherType(String voucher) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getLabel().equals(voucher))
                .findFirst()
                .orElse(null);
    }
}
