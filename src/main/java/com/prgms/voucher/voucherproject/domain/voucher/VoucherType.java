package com.prgms.voucher.voucherproject.domain.voucher;

import java.util.InputMismatchException;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED(1){
        @Override
        public Voucher createVoucher(long discount) {
            return new FixedAmountVoucher(discount);
        }
    },
    PERCENT(2) {
        @Override
        public Voucher createVoucher(long discount) {
            return new PercentDiscountVoucher(discount);
        }
    };

    private int voucherNum;

    public abstract Voucher createVoucher(long discount);

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
