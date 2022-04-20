package org.programmers.springbootbasic.voucher.model;

import java.time.LocalDateTime;
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
        return "FixedAmountVoucher" +
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
    public long getValue() {
        return amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    public static Voucher createVoucher(VoucherDTO voucherDTO) {
        return new FixedAmountVoucher(voucherDTO.getVoucherId(), voucherDTO.getValue(), voucherDTO.getCreatedAt());
    }
}
