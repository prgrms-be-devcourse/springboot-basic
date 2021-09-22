package org.prgrms.kdtspringhomework.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long ZERO_VOUCHER_AMOUNT = 0;
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validationAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    private void validationAmount(long amount) {
        if (amount < ZERO_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Amount should be positive");
        }
        if (amount == ZERO_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Amount should not be zero");
        }
        if (amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        }
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "fixed," +
                voucherId +
                "," + amount;
    }
}
