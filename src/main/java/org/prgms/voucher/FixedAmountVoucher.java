package org.prgms.voucher;

import org.prgms.validator.Validators;

import java.util.UUID;

public record FixedAmountVoucher(UUID customerId, long discountAmount, UUID voucherId) implements Voucher {

    public FixedAmountVoucher(UUID customerId, long discountAmount, UUID voucherId) {
        this.customerId = customerId;
        this.discountAmount = discountAmount;
        Validators.notNullAndEmptyCheck(voucherId);
        this.voucherId = voucherId;
    }

    @Override
    public long apply(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }
}
