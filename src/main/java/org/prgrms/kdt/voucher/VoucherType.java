package org.prgrms.kdt.voucher;

import java.util.Arrays;

public enum VoucherType {
    PERCENTAGE("PERCENT"),
    FIXED("FIXED");

    private final String strVoucherType;

    VoucherType(String strVoucherType) {
        this.strVoucherType = strVoucherType;
    }

    public static VoucherType findVoucher(String inputString) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.strVoucherType.equals(inputString))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
