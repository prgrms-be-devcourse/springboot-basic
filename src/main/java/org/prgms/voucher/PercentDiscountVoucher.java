package org.prgms.voucher;

import org.prgms.validator.DomainValidators;

import java.util.UUID;

public record PercentDiscountVoucher(UUID voucherId, long discountAmount) implements Voucher {

    public PercentDiscountVoucher(UUID voucherId, long discountAmount) {
        this.discountAmount = discountAmount;
        DomainValidators.notNullAndEmptyCheck(voucherId);
        this.voucherId = voucherId;
    }

    @Override
    public long apply(long beforeDiscount) {
        return (long) ((1 - (discountAmount / 100.0)) * beforeDiscount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return discountAmount;
    }
}
