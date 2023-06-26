package org.devcourse.springbasic.domain.voucher.domain;

import org.devcourse.springbasic.global.validator.Validator;
import org.devcourse.springbasic.global.validator.VoucherValidator;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final long FIXED_DISCOUNT_RATE = 500L;

    public FixedAmountVoucher() {
        super(UUID.randomUUID(), FIXED_DISCOUNT_RATE, VoucherType.FIXED_AMOUNT_VOUCHER);
        Validator<Voucher> voucherValidator = new VoucherValidator();
        voucherValidator.validate(this);
    }

    public FixedAmountVoucher(UUID voucherId, long fixedDiscountRate) {
        super(voucherId, fixedDiscountRate, VoucherType.FIXED_AMOUNT_VOUCHER);
        Validator<Voucher> voucherValidator = new VoucherValidator();
        voucherValidator.validate(this);
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - getDiscountRate();
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public long maxDiscountRate() {
        return FIXED_DISCOUNT_RATE;
    }

}
