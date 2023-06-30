package org.prgrms.application.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final double amount;

    public FixedAmountVoucher(UUID voucherId, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("금액은 양수여야 합니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    // 복사를 위한 생성자
    public FixedAmountVoucher(FixedAmountVoucher other) {
        this.voucherId = other.voucherId;
        this.amount = other.amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public double discount(double beforeDiscount) {
        double discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public Voucher copy() {
        return new FixedAmountVoucher(this);
    }

    @Override
    public String toString() {
        return "Fixed { " + "voucherId=" + voucherId + ", amount=" + amount + '}';
    }
}
