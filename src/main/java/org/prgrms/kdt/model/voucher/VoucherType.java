package org.prgrms.kdt.model.voucher;

import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("fixed"),
    PERCENT_DISCOUNT("percent");

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public static VoucherType getVoucherType(String voucherType) {
        return Stream.of(VoucherType.values())
                .filter(type -> type.getVoucherType().equalsIgnoreCase(voucherType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 바우처 타입입니다."));
    }

}
