package com.dev.voucherproject.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, LocalDateTime createdAt, long amount) {
        super(voucherId, createdAt);
        amountValidate(amount);
        this.amount = amount;
    }

    public long discount(long beforeDiscount) {
        if (amount > beforeDiscount) {
            return 0;
        }

        return beforeDiscount - amount;
    }

    @Override
    public long getDiscountFigure() {
        return this.amount;
    }

    private void amountValidate(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("고정 할인 정책 바우처의 금액은 음수가 될 수 없습니다.");
        }
    }
}
