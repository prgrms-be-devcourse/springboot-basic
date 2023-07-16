package com.programmers.springweekly.domain.voucher;

import com.programmers.springweekly.util.validator.VoucherValidator;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        VoucherValidator.validateVoucher(
                VoucherType.FIXED,
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
        return beforeDiscount - discountAmount;
    }

    @Override
    public long getVoucherAmount() {
        return discountAmount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

}
