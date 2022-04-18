package org.prgrms.kdtspringdemo.voucher.voucherdetail;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 100;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        validateDiscountAmount(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getAmount() {
        return percent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }

    public void validateDiscountAmount(long amount) {
        if (amount < MIN_DISCOUNT_AMOUNT || amount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("Percent 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
        }
    }
}
