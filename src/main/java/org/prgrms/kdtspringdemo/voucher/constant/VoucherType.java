package org.prgrms.kdtspringdemo.voucher.constant;

import java.util.Arrays;

public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType findVoucherType(String userVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(userVoucherType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력을 잘못하였습니다."));
    }
}
