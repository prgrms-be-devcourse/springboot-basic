package org.prgrms.kdtspringdemo.domain.voucher.type;

import java.util.Arrays;

public enum VoucherType {
    FIXED("You choose FixedAmountVoucher"),
    PERCENT("PercentDiscountVoucher"),
    None("Type wrong type");

    private final String stateInfo;

    VoucherType(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static VoucherType of(String inputVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(type -> String.valueOf(type).equalsIgnoreCase(inputVoucherType))
                .findFirst()
                .orElse(None);
    }

    public void writeStateInfo() {
        System.out.println(stateInfo);
    }
}
