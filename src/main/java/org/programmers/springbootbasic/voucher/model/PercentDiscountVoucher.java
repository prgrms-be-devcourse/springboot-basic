package org.programmers.springbootbasic.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < 0) throw new IllegalArgumentException("할인율은 0보다 커야 합니다.");
        if (percent == 0) throw new IllegalArgumentException("할인율은 0이 아니어야 합니다.");
        if (percent > 99) throw new IllegalArgumentException("할인율은 100보다 작아야 합니다.");
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher" +
                " voucherId: " + voucherId +
                " percent: " + percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - percent) / 100;
    }
}
