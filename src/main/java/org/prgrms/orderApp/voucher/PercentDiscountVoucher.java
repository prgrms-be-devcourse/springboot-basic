package org.prgrms.orderApp.voucher;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        Assert.isTrue(percent > 0L && percent <= 100L , "Percent 바우처 할인은 0%이상 100% 이하만 가능합니다.");
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - (long)((float)beforeDiscount * ((float)percent / 100));
    }

    @Override
    public long getVoucherAmount() {
        return percent;
    }


    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';

    }
}
