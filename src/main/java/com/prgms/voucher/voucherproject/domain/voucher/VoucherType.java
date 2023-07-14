package com.prgms.voucher.voucherproject.domain.voucher;

import java.util.InputMismatchException;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED(1),
    PERCENT(2);

    private int voucherNum;

    VoucherType(int voucherNum) {
        this.voucherNum = voucherNum;
    }

    public static VoucherType getSelectedVoucherType(int selectedNum) {
        return Stream.of(VoucherType.values())
                .filter(voucherType -> voucherType.voucherNum == selectedNum)
                .findFirst()
                .orElseThrow(() -> new InputMismatchException("존재하지 않는 바우처 타입입니다."));
    }

}
