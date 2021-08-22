package org.prgrms.kdtspringorder;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

    @Override
    public String toString() {
        return MessageFormat.format("[PercentDiscountVoucher - 할인률 : {0}]", percent);
    }

    public PercentDiscountVoucher(UUID voucherId,long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId(){
        return voucherId;
    }

    public long discount(long beforeDiscount){
        return beforeDiscount *(percent/100);
    }
}