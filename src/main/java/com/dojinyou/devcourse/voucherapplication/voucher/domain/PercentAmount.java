package com.dojinyou.devcourse.voucherapplication.voucher.domain;

public class PercentAmount extends VoucherAmount {
    private static final int MIN_AMOUNT = 0;
    private static final int MAX_AMOUNT = 100;
    public static final String ERROR_MESSAGE_OUT_OT_RANGE = "voucher amount 범위를 벗어났습니다.";

    public PercentAmount(int amount) {
        super(amount);
        validate(amount);
    }

    @Override
    public void validate(int amount) {
        if (amount < MIN_AMOUNT || MAX_AMOUNT < amount) {
            throw new IllegalArgumentException(ERROR_MESSAGE_OUT_OT_RANGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        PercentAmount otherVoucherAmount = (PercentAmount) other;
        if (this == other || this.getAmount() == otherVoucherAmount.getAmount()) {
            return true;
        }
        return false;
    }

    public double getRemainRate() {
        return 1 - getDiscountRate();
    }

    private double getDiscountRate() {
        return getAmount() / 100.0;
    }
}
