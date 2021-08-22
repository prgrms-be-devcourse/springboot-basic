package org.prgrms.kdt.Voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private  final TypeStatus type=TypeStatus.Percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;

    }

    @Override
    public long getVoucherdiscount() {
        return percent;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public TypeStatus getType() {
        return type;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }



}