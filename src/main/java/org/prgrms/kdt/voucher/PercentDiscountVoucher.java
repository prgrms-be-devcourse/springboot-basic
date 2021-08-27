package org.prgrms.kdt.voucher;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private final long percent;
    private final UUID voucherId;

    public PercentDiscountVoucher(UUID voucherId, long percent){
        this.percent = percent;
        this.voucherId = voucherId;
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount*(percent/100);
    }

    @Override
    public UUID getID() {
        return voucherId;
    }

    @Override
    public String toString(){
        return percent + " percent discount voucher";
    }
}
