package org.devcourse.voucher.voucher.model;

import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1", FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("2", PercentDiscountVoucher::new),
    NONE("", null);

    private String option;
    BiFunction<UUID, Long, Voucher> creator;

    VoucherType(String option, BiFunction<UUID, Long, Voucher> creator) {
        this.option = option;
        this.creator = creator;
    }

    public static VoucherType discriminate(String input) {
        VoucherType voucherType;

        switch (input) {
            case "1" -> voucherType = FIXED_AMOUNT_VOUCHER;
            case "2" -> voucherType = PERCENT_DISCOUNT_VOUCHER;
            default -> voucherType = NONE;
        }
        return voucherType;
    }

    public Voucher voucherCreator(UUID voucherId, long discount) {
        return creator.apply(voucherId, discount);
    }
}
