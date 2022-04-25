package me.programmers.springboot.basic.springbootbasic.voucher;

import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED ,
    PERCENT;

    public static VoucherType getVoucherStatus(String inputVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(inputVoucherType.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입 입력 " + inputVoucherType));
    }

    public static Voucher getSpecificVoucher(VoucherType voucherType, ConsoleInput consoleInput) {
        Voucher voucher = null;

        switch (voucherType) {
            case FIXED:
                long amount = Long.parseLong(consoleInput.inputCommand("고정 할인 금액 입력"));
                voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
                break;
            case PERCENT:
                long percent = Long.parseLong(consoleInput.inputCommand("할인율 입력"));
                voucher = new PercentAmountVoucher(UUID.randomUUID(), percent);
                break;
            default:
                break;
        }
        return voucher;
    }

}
