package org.prgrms.kdt.voucher;

import java.util.Arrays;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:29 오후
 */
public enum VoucherType {
    FIX, PERCENT;

    public static boolean isValue(String type) {
        return Arrays.stream(VoucherType.values())
                .anyMatch(voucherType -> voucherType.name().equals(type));
    }
}
