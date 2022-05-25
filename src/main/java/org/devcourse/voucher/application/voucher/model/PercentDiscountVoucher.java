package org.devcourse.voucher.application.voucher.model;


import java.util.UUID;

import static org.devcourse.voucher.core.exception.ErrorType.INPUT_NEGATIVE_NUMBERS;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        if (percent > 100) {
            percent = 100;
        }
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public void setDiscount(long percent) {
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (beforeDiscount < 0) {
            throw new IllegalArgumentException(INPUT_NEGATIVE_NUMBERS.message());
        }
        double discountRate = (double)(100 - percent) / 100;

        return (long)(beforeDiscount * discountRate);
    }

    @Override
    public long getDiscount() {
        return percent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher\t" +
                voucherId + "\t" +
                percent + "%" + "\t";
    }
}
