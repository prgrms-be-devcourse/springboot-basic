package org.prgrms.kdt.voucher;

import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 10:16 오후
 */
public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    public String toString() {
        return new StringBuilder().append("PercentDiscountVoucher {")
                .append("voucherId = ").append(voucherId)
                .append(", percent = ").append(percent)
                .append('}').toString();
    }
}