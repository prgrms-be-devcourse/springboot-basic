package org.programmers.springbootbasic.voucher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private long amount;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    private void validateAmount(long amount) {
        if (amount <= 0) throw new IllegalArgumentException("할인된 금액은 0보다 커야 합니다.");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("할인금액은 %d보다 작아야 합니다.".formatted(MAX_VOUCHER_AMOUNT));
    }

    @Override
    public String toString() {
        return "FIXED" +
                " voucherId: " + voucherId +
                " amount: " + amount +
                " createdAt: " + createdAt;
    }

    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        if (discountedAmount < 0) throw new ArithmeticException("결제 금액보다 할인 금액이 더 큽니다.");
        return discountedAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    public long getValue() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void changeValue(long value) {
        this.amount = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}
