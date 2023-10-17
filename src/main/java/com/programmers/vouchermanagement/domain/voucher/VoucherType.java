package com.programmers.vouchermanagement.domain.voucher;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT(1, "Fixed amount Voucher") {
        @Override
        public Voucher createVoucher(UUID id, Long amount) {
            return new FixedAmountVoucher(id, amount);
        }
    },
    PERCENT_DISCOUNT(2, "Percent discount Voucher") {
        @Override
        public Voucher createVoucher(UUID id, Long amount) {
            return new PercentDiscountVoucher(id, amount);
        }
    };

    private final int id;
    private final String description;

    VoucherType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public abstract Voucher createVoucher(UUID id, Long amount);

    public static void printAllDescriptionsToConsole() {
        for (VoucherType vt : VoucherType.values()) {
            System.out.println((vt.ordinal() + 1) + ". " + vt.description + (vt.id == 1 ? " (default)" : ""));
        }
    }

    public static VoucherType select(int id) {
        return Arrays.stream(values())
                .filter(vt -> vt.id == id)
                .findFirst()
                .orElse(FIXED_AMOUNT);
    }
}
