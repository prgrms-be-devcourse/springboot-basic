package org.devcourse.voucher.application.voucher.model;

import org.devcourse.voucher.core.exception.ErrorType;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1", "FixedAmountVoucher", FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("2", "PercentDiscountVoucher", PercentDiscountVoucher::new);

    private final String option;
    private final String name;
    private final BiFunction<UUID, Long, Voucher> creator;

    VoucherType(String option, String name, BiFunction<UUID, Long, Voucher> creator) {
        this.option = option;
        this.name = name;
        this.creator = creator;
    }

    public String getOption() {
        return option;
    }

    public String getName() {
        return name;
    }

    public static VoucherType optionDiscriminate(String input) {
        return Arrays.stream(values())
                .filter(type -> type.getOption().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorType.INVALID_TYPE.message()));
    }

    public static VoucherType nameDiscriminate(String input) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorType.INVALID_TYPE.message()));
    }

    public Voucher voucherCreator(UUID voucherId, long discount) {
        return creator.apply(voucherId, discount);
    }
}
