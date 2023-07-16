package com.programmers.springweekly.domain.voucher;

import com.programmers.springweekly.util.validator.VoucherValidator;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountAmount;

    public PercentDiscountVoucher(UUID voucherId, long discountAmount) {
        VoucherValidator.validateVoucher(
                VoucherType.PERCENT,
                String.valueOf(discountAmount)
        );

        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount - beforeDiscount * ((double) discountAmount / 100));
    }

    @Override
    public long getVoucherAmount() {
        return discountAmount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

}
