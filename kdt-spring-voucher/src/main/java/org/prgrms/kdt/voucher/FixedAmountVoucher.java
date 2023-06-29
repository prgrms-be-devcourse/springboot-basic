package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;


public class FixedAmountVoucher implements Voucher {
    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discountAppliedPrice(long beforeDiscount) {
        long finalPrice = beforeDiscount - amount;
        return finalPrice > 0 ? finalPrice : 0;
    }

    }

    @Override
    public String toString() {
        return String.format("%-25s id: %s fixedAmount: %d", "[FixedAmountVoucher]", voucherId.toString(), amount);
    }
}
