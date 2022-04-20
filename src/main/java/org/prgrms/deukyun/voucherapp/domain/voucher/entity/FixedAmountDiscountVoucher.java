package org.prgrms.deukyun.voucherapp.domain.voucher.entity;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정액 할인 바우처
 */
public class FixedAmountDiscountVoucher implements Voucher {

    private final long amount;
    private UUID id;

    public FixedAmountDiscountVoucher(long amount) {
        checkArgument(amount > 0, "amount must be positive.");

        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        checkArgument(beforeDiscountPrice - amount >= 0, "discounted price must be non-negative");

        return beforeDiscountPrice - amount;
    }

    @Override
    public String toDisplayString() {
        return new StringBuilder("[Fixed Amount Discount Voucher]")
                .append(" id : ").append(id.toString(), 0, 8)
                .append(", amount  : ").append(amount)
                .toString();
    }
}
