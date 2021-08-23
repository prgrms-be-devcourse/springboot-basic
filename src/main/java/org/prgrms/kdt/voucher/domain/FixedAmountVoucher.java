package org.prgrms.kdt.voucher.domain;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MIN_VOUCHER_AMOUNT = 0;
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validate(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public void validate(long amount) {
        if (amount < MIN_VOUCHER_AMOUNT) {
            throw new InvalidArgumentException(ErrorMessage.MORE_THAN_MIN_VOUCHER_AMOUNT);
        }

        if (amount == MIN_VOUCHER_AMOUNT) {
            throw new InvalidArgumentException(ErrorMessage.NOT_BE_ZERO_VOUCHER_AMOUNT);
        }

        if (amount > MAX_VOUCHER_AMOUNT) {
            throw new InvalidArgumentException(ErrorMessage.LESS_THAN_MAX_VOUCHER_AMOUNT);
        }
    }

    @Override
    public UUID voucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountAmount = beforeDiscount - amount;
        if (discountAmount < 0) {
            throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
        }
        return discountAmount;
    }

    @Override
    public String toString() {
        return "Fixed " +
                voucherId +
                " " + amount;
    }

}
