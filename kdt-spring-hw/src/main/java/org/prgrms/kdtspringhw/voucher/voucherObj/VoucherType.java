package org.prgrms.kdtspringhw.voucher.voucherObj;

import org.prgrms.kdtspringhw.utils.Command;

import java.util.Arrays;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("fix"),
    PERCENT_DISCOUNT_VOUCHER("per"),
    ELSE("");
    private final String name;

    VoucherType(String name){
        this. name = name;
    }
    public static VoucherType getVoucherType(String name){
        return Arrays.stream(values())//values는 배열을 보낸다.
                .filter(commandType -> commandType.name.equals(name))
                .findAny()
                .orElse(ELSE);
    }
}
