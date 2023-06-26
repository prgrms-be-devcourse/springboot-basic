package org.devcourse.springbasic.voucher;

import org.devcourse.springbasic.validator.Validator;
import org.devcourse.springbasic.validator.VoucherValidator;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final long PERCENT_MAX_DISCOUNT_RATE = 50L;
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
        Validator<Voucher> voucherValidator = new VoucherValidator<>();
        voucherValidator.validate(this);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountRate() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100L);
    }

    @Override
    public long maxDiscountRate() {
        return PERCENT_MAX_DISCOUNT_RATE;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher" +
                "," + voucherId +
                "," + percent;
    }
}
