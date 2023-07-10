package com.prgmrs.voucher.model;

import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.DiscountValue;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Amount amount;

    public FixedAmountVoucher(UUID voucherId, Amount amount) {
        if (amount.getValue() < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public DiscountValue discount(DiscountValue beforeDiscount) {
        if (amount.getValue() > beforeDiscount.getValue()) {
            return new DiscountValue(0);
        }
        return new DiscountValue(beforeDiscount.getValue() - amount.getValue());
    }
}
