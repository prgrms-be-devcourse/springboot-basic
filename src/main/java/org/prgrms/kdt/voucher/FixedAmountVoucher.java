package org.prgrms.kdt.voucher;

import java.io.Serializable;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {
    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(UUID voucherId, long amount){
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount-amount;
    }

    @Override
    public UUID getID() {
        return voucherId;
    }

    @Override
    public String toString(){
        return amount+" fixed amount voucher";
    }
}
