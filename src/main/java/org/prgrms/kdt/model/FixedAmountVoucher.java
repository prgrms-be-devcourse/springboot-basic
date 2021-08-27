package org.prgrms.kdt.model;

import java.text.MessageFormat;
import org.prgrms.kdt.exception.InvalidDataException;

import java.io.Serializable;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    private void validateAmount(long amount) {
        if (amount < 0) {
            throw new InvalidDataException(
                MessageFormat.format("amount should be positive: {0}", amount));
        }
        if (amount == 0) {
            throw new InvalidDataException(
                MessageFormat.format("amount should not be zero: {0}", amount));
        }
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "voucherId=" + voucherId +
            ", amount=" + amount +
            '}';
    }
}
