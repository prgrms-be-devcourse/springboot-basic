package org.programmer.kdtspringboot.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final Long percent;
    public PercentDiscountVoucher(UUID voucherId, Long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public String getInfo() {
        return voucherId+","+percent+","+this.getClass();
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                ", class=" + this.getClass() +
                '}';
    }
}
