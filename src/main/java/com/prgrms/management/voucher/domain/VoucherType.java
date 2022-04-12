package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessage;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

public enum VoucherType {
    FIXED((amount) -> new FixedAmountVoucher(amount)),
    PERCENT((amount) -> new PercentAmountVoucher(amount));

    private final Function<Long, Voucher> voucher;

    VoucherType(Function<Long, Voucher> voucher) {
        this.voucher = voucher;
    }

    public static VoucherType of(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(VoucherType.class + ErrorMessage.NOT_VOUCHER_TYPE.getMessage()));
    }
    
    public Voucher createVoucher(String inputAmount) {
        long amount;
        try {
            amount = Long.parseLong(inputAmount);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(VoucherType.class + ErrorMessage.INCORRECT_NUMBER_FORMAT.getMessage());
        }
        return voucher.apply(amount);
    }
}
