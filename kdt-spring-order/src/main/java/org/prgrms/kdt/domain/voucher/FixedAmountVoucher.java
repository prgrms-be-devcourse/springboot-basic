package org.prgrms.kdt.domain.voucher;

import java.text.MessageFormat;
import java.util.UUID;

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
    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[FixedAmountVoucher - 할인 금액 : {0}]", amount);
    }
}
