package org.prgrms.kdt.domain.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return amount;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("id={0} type={1} value={2}", voucherId, 0, amount);
    }
}
