package com.prgms.voucher.voucherproject.domain;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("1"),
    PERCENT("2");

    private String voucherNum;

    VoucherType(String voucherNum) {
        this.voucherNum = voucherNum;
    }

    public static VoucherType getSelectedVoucherType(String selectedNum) {
        return Stream.of(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.voucherNum, selectedNum))
                .findFirst()
                .orElseThrow(() -> new InputMismatchException("존재하지 않는 바우처 타입입니다."));
    }

}
