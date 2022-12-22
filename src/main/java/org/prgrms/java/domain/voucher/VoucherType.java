package org.prgrms.java.domain.voucher;

import org.prgrms.java.exception.badrequest.VoucherBadRequestException;

import java.util.Arrays;

public enum VoucherType {
    FIXED(1, "FixedAmountVoucher"),
    PERCENT(2, "PercentDiscountVoucher");

    private final int id;
    private final String typeName;

    VoucherType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public static VoucherType of(int id) {
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.id == id)
                .findAny()
                .orElseThrow(() -> new VoucherBadRequestException("잘못된 바우처 타입입니다."));
    }

    public static VoucherType of(String typeName) {
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.typeName.equals(typeName))
                .findAny()
                .orElseThrow(() -> new VoucherBadRequestException("잘못된 바우처 타입입니다."));
    }

    @Override
    public String toString() {
        return typeName;
    }
}
