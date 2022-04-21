package org.prgrms.deukyun.voucherapp.domain.voucher.entity;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정액 할인 바우처
 */
public class FixedAmountDiscountVoucher extends Voucher {

    private final long amount;

    public FixedAmountDiscountVoucher(long amount) {
        this(UUID.randomUUID(), amount);
    }

    public FixedAmountDiscountVoucher(UUID id, long amount) {
        checkArgument(amount > 0, "amount must be positive.");

        super.setId(id);
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        checkArgument(beforeDiscountPrice - amount >= 0, "discounted price must be non-negative");

        return beforeDiscountPrice - amount;
    }

    @Override
    public String toDisplayString() {
        return new StringBuilder("[Fixed Amount Discount Voucher]")
                .append(" id : ").append(super.getId().toString(), 0, 8)
                .append(", amount  : ").append(amount)
                .toString();
    }
}
