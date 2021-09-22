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
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 바우처 타입 <" +name+">를 입력하셨습니다.\n새로운 "));
    }

    public String getName(){
        return name;
    }
}
