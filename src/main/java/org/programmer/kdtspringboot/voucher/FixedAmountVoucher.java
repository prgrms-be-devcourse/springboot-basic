package org.programmer.kdtspringboot.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final Long discountAmount;
    private final VoucherType type = VoucherType.FixedAmountVoucher;
    private final LocalDateTime createdAt;
    private final static Integer MAX_AMOUNT = 10000;
    private final static Integer MIN_AMOUNT = 0;

    public FixedAmountVoucher(UUID voucherId, Long discountAmount, LocalDateTime createdAt) {
        validateAmount(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount - discountAmount > 0 ? beforeDiscount - discountAmount : 0;
    }

    @Override
    public Long getValue() {
        return discountAmount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }

    private void validateAmount(Long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(MIN_AMOUNT + "~" + MAX_AMOUNT + " 값을 입력해주세요.");
        }
    }

}
