package org.prgms.voucher;

import org.prgms.validator.DomainValidators;

import java.util.UUID;

public record FixedAmountVoucher(UUID voucherId, long discountAmount) implements Voucher {

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.discountAmount = discountAmount;
        DomainValidators.notNullAndEmptyCheck(voucherId);
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

}
