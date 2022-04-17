package me.programmers.springboot.basic.springbootbasic.voucher;

import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED, PERCENT;

    public static VoucherType getVoucherStatus(String inputVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(inputVoucherType.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입 입력 " + inputVoucherType));
    }

    public static Voucher getSpecificVoucher(VoucherType voucherType) {
        Voucher voucher = null;

        switch (voucherType) {
            case FIXED:
                voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
                break;
            case PERCENT:
                voucher = new PercentAmountVoucher(UUID.randomUUID(), 50);
                break;
            default:
                break;
        }
        return voucher;
    }
}
