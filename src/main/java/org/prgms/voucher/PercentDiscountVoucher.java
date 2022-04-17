package org.prgms.voucher;

import org.prgms.validator.Validators;

import java.util.UUID;

public record PercentDiscountVoucher(UUID customerId, long discountPercent, UUID voucherId) implements Voucher {

    public PercentDiscountVoucher(UUID customerId, long discountPercent, UUID voucherId) {
        this.discountPercent = discountPercent;
        Validators.notNullAndEmptyCheck(voucherId);
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    @Override
    public long apply(long beforeDiscount) {
        return (long) ((1 - (discountPercent / 100.0)) * beforeDiscount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return discountPercent;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }
}
