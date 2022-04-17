package org.prgrms.kdtspringdemo.voucher.voucherdetail;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 1_000_000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validateDiscountAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }

    public void validateDiscountAmount(long amount) {
        if (amount < MIN_DISCOUNT_AMOUNT || amount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("Fixed 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
        }
    }
}
