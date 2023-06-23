package com.programmers.console.util;

import com.programmers.console.exception.VoucherCommandException;
import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.util.TriFunction;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherPolicy {
    FIXED_AMOUNT("1", FixedAmountVoucher::new),
    PERCENT_DISCOUNT("2", PercentDiscountVoucher::new);

    private final String command;
    private final TriFunction<UUID, Long, LocalDate, Voucher> constructor;

    VoucherPolicy(String command, TriFunction<UUID, Long, LocalDate, Voucher> constructor) {
        this.command = command;
        this.constructor = constructor;
    }

    public static VoucherPolicy findByCommand(String command) {
        return Arrays.stream(VoucherPolicy.values())
                .filter(voucherPolicy -> voucherPolicy.command.equals(command))
                .findFirst()
                .orElseThrow(VoucherCommandException::new);

    }

    public Voucher constructor(UUID voucherId, long discountAmount, LocalDate localDate) {
        return constructor.apply(voucherId, discountAmount, localDate);
    }

}
