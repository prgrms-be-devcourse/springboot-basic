package org.programmers.voucher.domain;

import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("Fixed"),
    PERCENT_DISCOUNT_VOUCHER("Percent");

    private final String alias;

    VoucherType(String alias){
        this.alias = alias;
    }

    public boolean isMatches(String input) {
        return input.equals(alias);
    }

    public String getAlias() {
        return alias;
    }

    public static Voucher makeVoucher(VoucherType type, long value) {
        switch (type) {
            case FIXED_AMOUNT_VOUCHER -> {
                if (value < 0) {
                    throw new IllegalArgumentException();
                }
                return new FixedAmountVoucher(UUID.randomUUID(), value);
            }
            case PERCENT_DISCOUNT_VOUCHER -> {
                if (value < 0 || value > 100) {
                    throw new IllegalArgumentException();
                }
                return new PercentDiscountVoucher(UUID.randomUUID(), value);
            }
        }
        return null;
    }
}
