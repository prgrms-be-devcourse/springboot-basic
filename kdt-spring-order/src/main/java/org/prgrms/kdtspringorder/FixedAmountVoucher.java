package org.prgrms.kdtspringorder;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    @Override
    public String toString() {
        return MessageFormat.format("[FixedAmountVoucher - 할인 금액 : {0}]", amount);
    }


    public FixedAmountVoucher(UUID voucherId,long amount) {
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId(){
        return voucherId;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }
}
