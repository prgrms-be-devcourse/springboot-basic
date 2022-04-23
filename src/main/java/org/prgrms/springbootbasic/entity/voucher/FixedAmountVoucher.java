package org.prgrms.springbootbasic.entity.voucher;

import java.util.UUID;
import org.prgrms.springbootbasic.exception.AmountRangeMaxException;
import org.prgrms.springbootbasic.exception.AmountRangeMinException;

public class FixedAmountVoucher extends Voucher {

    private static final int MIN_RANGE = 0;
    private static final int MAX_RANGE = 100000;

    private final int amount;

    public FixedAmountVoucher(UUID voucherId, int amount) {
        super(voucherId);

        validateAmountRange(amount);
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, int amount) {
        super(voucherId, customerId);

        validateAmountRange(amount);
        this.amount = amount;
    }

    private void validateAmountRange(int amount) {
        if (amount <= MIN_RANGE) {
            throw new AmountRangeMinException();
        }
        if (amount >= MAX_RANGE) {
            throw new AmountRangeMaxException();
        }
    }

    @Override
    public boolean isFixed() {
        return true;
    }

    public int getAmount() {
        return amount;
    }
}
