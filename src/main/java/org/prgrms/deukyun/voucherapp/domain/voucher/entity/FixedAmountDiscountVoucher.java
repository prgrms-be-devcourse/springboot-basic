package org.prgrms.deukyun.voucherapp.domain.voucher.entity;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정액 할인 바우처
 */
public class FixedAmountDiscountVoucher implements Voucher {

    private UUID id;
    public final long amount;

    public FixedAmountDiscountVoucher(long amount) {
        checkArgument(amount > 0, "amount must be positive.");

        this.amount = amount;
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
}
