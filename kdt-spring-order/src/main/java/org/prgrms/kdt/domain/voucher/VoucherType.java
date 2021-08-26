package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.exception.InvalidVoucherTypeException;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("1"),
    PERCENT_DISCOUNT("2");

    private final String voucherType;

    VoucherType(String voucherType){
        this.voucherType = voucherType;
    }

    public static VoucherType findByVoucherType(String voucherType){
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.voucherType.equals(voucherType))
                .findAny()
                .orElseThrow(()->new InvalidVoucherTypeException("유효하지 않은 바우처 유형 입니다."));
    }
}
