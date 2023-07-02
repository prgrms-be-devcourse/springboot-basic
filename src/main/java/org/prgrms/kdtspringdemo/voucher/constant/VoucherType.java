package org.prgrms.kdtspringdemo.voucher.constant;

import java.util.Arrays;

public enum VoucherType {
    FIXED,
    PERCENT;

    private static final String CANT_FIND_VOUCHER_TYPE = "알맞는 바우처 형식이 없습니다.";

    public static VoucherType findVoucherType(String userVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(userVoucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(CANT_FIND_VOUCHER_TYPE));
    }
}
