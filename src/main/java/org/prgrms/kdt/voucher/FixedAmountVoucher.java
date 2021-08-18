package org.prgrms.kdt.voucher;

import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 9:11 오후
 */
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

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("FixedAmountVoucher {")
                .append("voucherId = ").append(voucherId)
                .append(", amount = ").append(amount)
                .append('}').toString();
    }
}
