package org.devcourse.voucher.application.voucher.model;

import java.util.UUID;

import static org.devcourse.voucher.core.exception.ErrorType.INPUT_NEGATIVE_NUMBERS;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public void setDiscount(long amount) {
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (beforeDiscount < 0) {
            throw new IllegalArgumentException(INPUT_NEGATIVE_NUMBERS.message());
        }
        long afterMoney = beforeDiscount - amount;
        return afterMoney > 0 ? afterMoney : 0;
    }

    @Override
    public long getDiscount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher\t" +
                voucherId + "\t" +
                amount + "원";
    }
}
