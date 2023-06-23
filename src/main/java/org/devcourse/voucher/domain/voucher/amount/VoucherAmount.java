package org.devcourse.voucher.domain.voucher.amount;

import org.devcourse.voucher.domain.voucher.VoucherType;

public abstract class VoucherAmount {
    private final int amount;

    public VoucherAmount(int amount) {
        this.amount = setAmount(amount);
    }

    public static VoucherAmount of(VoucherType type, int amount) {
        return switch (type) {
            case PERCENT -> new PercentAmount(amount);
            case FIX -> new FixedAmount(amount);
        };
    }

    public int getAmount() {
        return amount;
    }

    private int setAmount(int amount) {
        if (validate(amount)) {
            return amount;
        }

        throw new RuntimeException("바우처 금액 범위 오류");
    }

    public abstract boolean validate(int amount);
}
