package org.devcourse.voucher.voucher.model;

import java.util.UUID;

import static org.devcourse.voucher.error.ErrorType.INPUT_NEGATIVE_NUMBERS;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
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
