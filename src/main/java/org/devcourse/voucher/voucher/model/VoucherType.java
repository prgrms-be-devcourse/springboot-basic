package org.devcourse.voucher.voucher.model;

import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1", "FixedAmountVoucher", FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("2", "PercentDiscountVoucher", PercentDiscountVoucher::new),
    NONE("", "", null);

    private final String option;
    private final String name;
    private final BiFunction<UUID, Long, Voucher> creator;

    VoucherType(String option, String name, BiFunction<UUID, Long, Voucher> creator) {
        this.option = option;
        this.name = name;
        this.creator = creator;
    }

    public static VoucherType discriminate(String input) {
        VoucherType voucherType;

        switch (input) {
            case "1", "FixedAmountVoucher" -> voucherType = FIXED_AMOUNT_VOUCHER;
            case "2", "PercentDiscountVoucher" -> voucherType = PERCENT_DISCOUNT_VOUCHER;
            default -> voucherType = NONE;
        }
        return voucherType;
    }

    public Voucher voucherCreator(UUID voucherId, long discount) {
        return creator.apply(voucherId, discount);
    }
}
