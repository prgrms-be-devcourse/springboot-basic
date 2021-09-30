package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.voucher.exception.InvalidVoucherTypeException;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT("1"),
    PERCENT_DISCOUNT("2");

    private final String voucherType;

    VoucherType(String voucherType){
        this.voucherType = voucherType;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public static VoucherType findByVoucherType(String voucherType){
        return Arrays.stream(VoucherType.values())
                .filter(type -> type.voucherType.equals(voucherType))
                .findAny()
                .orElseThrow(()->new InvalidVoucherTypeException(ExceptionMessage.INVALID_VOUCHER_TYPE.getMessage()));
    }
}
