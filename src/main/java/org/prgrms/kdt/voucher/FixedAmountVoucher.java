package org.prgrms.kdt.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(final UUID voucherId, final long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(final long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("FixedAmountVoucher( {0}원 )", amount);
    }
}
