package org.devcourse.springbasic.domain.voucher.domain;

import org.devcourse.springbasic.global.validator.Validator;
import org.devcourse.springbasic.global.validator.VoucherValidator;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private static final long PERCENT_DISCOUNT_RATE = 50L;

    public PercentDiscountVoucher() {
        super(UUID.randomUUID(), PERCENT_DISCOUNT_RATE, VoucherType.PERCENT_DISCOUNT_VOUCHER);
        Validator<Voucher> voucherValidator = new VoucherValidator();
        voucherValidator.validate(this);
    }

    public PercentDiscountVoucher(UUID voucherId, Long percent_discount_rate) {
        super(voucherId, percent_discount_rate, VoucherType.PERCENT_DISCOUNT_VOUCHER);
        Validator<Voucher> voucherValidator = new VoucherValidator();
        voucherValidator.validate(this);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (getDiscountRate() / 100L);
    }

    @Override
    public long maxDiscountRate() {
        return PERCENT_DISCOUNT_RATE;
    }

}
