package org.prgrms.kdt.utils;


import org.prgrms.kdt.domain.voucher.VoucherException;
import org.prgrms.kdt.exception.ErrorMessage;

public enum VoucherType {


    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String matchString;

    VoucherType(String matchString) {
        this.matchString = matchString;
    }

    public static VoucherType of(String input) {
        return java.util.Arrays.stream(values())
                .filter(voucherType -> voucherType.matchString.equals(input))
                .findFirst()
                .orElseThrow(() -> new VoucherException(ErrorMessage.INVALID_TYPE));
    }
}
