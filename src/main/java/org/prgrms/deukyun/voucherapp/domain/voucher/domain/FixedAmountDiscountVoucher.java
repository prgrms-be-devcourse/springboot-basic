package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import lombok.Getter;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 정액 할인 바우처
 */
@Getter
public class FixedAmountDiscountVoucher extends Voucher {

    /**
     * 할인액
     */
    private final long amount;

    public FixedAmountDiscountVoucher(long amount) {
        this(UUID.randomUUID(), amount);
    }

    public FixedAmountDiscountVoucher(UUID id, long amount) {
        super(id);
        checkArgument(amount > 0, "amount must be positive.");

        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscountPrice) {
        checkArgument(beforeDiscountPrice - amount >= 0, "discounted price must be non-negative");

        return beforeDiscountPrice - amount;
    }

}
