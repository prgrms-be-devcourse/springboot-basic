package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.Voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT("F"), PERCENT_DISCOUNT("P");

    private final String inputCommand;

    VoucherType(String p) {
        this.inputCommand = p;
    }

    public static boolean isInVoucherType(String command) {
        long count = Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.inputCommand.equals(command))
                .count();
        return count != 0;
    }

    private static VoucherType findByCommand(String command) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.inputCommand.equals(command))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Optional<Voucher> getVoucher(String inputCommand, long value) {
        Optional<Voucher> voucher = Optional.empty();

        switch (findByCommand(inputCommand)) {
            case FIXED_AMOUNT -> voucher = Optional.of(new FixedAmountVoucher(UUID.randomUUID(), value));
            case PERCENT_DISCOUNT -> voucher = Optional.of(new PercentDiscountVoucher(UUID.randomUUID(), value));
        }
        return voucher;
    }

    ;
};
