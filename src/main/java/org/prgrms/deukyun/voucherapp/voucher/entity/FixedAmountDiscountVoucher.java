package org.prgrms.deukyun.voucherapp.voucher.entity;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정액 할인 바우처
 */
public class FixedAmountDiscountVoucher implements Voucher {

    private final UUID id;
    public final long amount;

    public FixedAmountDiscountVoucher(long amount) {
        checkArgument(amount > 0, "amount must be positive.");

        this.id = UUID.randomUUID();
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return id;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        checkDiscountApplicable(beforeDiscountPrice);

        return beforeDiscountPrice - amount;
    }

    private void checkDiscountApplicable(long beforeDiscountPrice) {
        if (beforeDiscountPrice - amount < 0) {
            throw new IllegalArgumentException("discounted price must be positive");
        }
    }
}
