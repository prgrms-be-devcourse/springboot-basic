package org.prgms.voucher;

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
    public String toString() {
        return MessageFormat.format("[FixedAmountVoucher] voucherId : {0}, amount : {1}", voucherId, amount) ;
    }


    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }
}
