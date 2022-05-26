package org.programmers.voucher.domain;

import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("Fixed", "양수 값을 입력하세요."),
    PERCENT_DISCOUNT_VOUCHER("Percent", "0~100 사이의 값을 입력하세요.");

    private final String alias;
    private final String description;

    VoucherType(String alias, String description){
        this.alias = alias;
        this.description = description;
    }

    public boolean isMatches(String input) {
        return input.equals(alias);
    }

    public String getAlias() {
        return alias;
    }

    public String getDescription() {
        return description;
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
