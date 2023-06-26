package org.devcourse.springbasic.voucher;

import org.devcourse.springbasic.validator.Validator;
import org.devcourse.springbasic.validator.VoucherValidator;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final long FIXED_MAX_DISCOUNT_RATE = 50L;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        Validator<Voucher> voucherValidator = new VoucherValidator<>();
        voucherValidator.validate(this);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountRate() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public long maxDiscountRate() {
        return FIXED_MAX_DISCOUNT_RATE;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher," + voucherId + "," + amount;
    }
}
