package com.prgms.voucher.voucherproject.domain;

import java.util.InputMismatchException;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED(1, "fixed"),
    PERCENT(2, "percent");

    private int voucherNum;
    private String voucherType;

    VoucherType(int voucherNum, String voucherType) {
        this.voucherNum = voucherNum;
        this.voucherType = voucherType;
    }

    public static VoucherType getSelectedVoucherType(int selectedNum){
        return Stream.of(VoucherType.values())
                .filter(voucherType -> voucherType.voucherNum == selectedNum)
                .findFirst()
                .orElseThrow(() -> new InputMismatchException("존재하지 않는 바우처 타입입니다."));
    }
}
