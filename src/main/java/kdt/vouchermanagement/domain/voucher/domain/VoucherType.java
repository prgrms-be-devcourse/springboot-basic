package kdt.vouchermanagement.domain.voucher.domain;

import java.util.Arrays;
import java.util.Objects;

public enum VoucherType {
    NONE(0),
    FIXED_AMOUNT(1),
    PERCENT_DISCOUNT(2);

    private int voucherTypeNum;

    VoucherType(int voucherTypeNum) {
        this.voucherTypeNum = voucherTypeNum;
    }

    public static VoucherType from(int voucherTypeNum) {
        return Arrays.stream(values())
                .filter(o -> Objects.equals(o.voucherTypeNum, voucherTypeNum))
                .findFirst()
                .orElse(NONE);
    }
}
