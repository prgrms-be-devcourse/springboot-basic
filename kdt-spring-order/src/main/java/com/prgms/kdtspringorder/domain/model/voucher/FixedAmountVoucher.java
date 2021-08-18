package com.prgms.kdtspringorder.domain.model.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long discountAmount;
    private boolean isUsed = false;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public long discount(long beforeDiscountAmount) {
        return beforeDiscountAmount - discountAmount;
    }

    @Override
    public void useVoucher() {
        if (isUsed) {
            throw new RuntimeException("이미 사용한 바우처입니다.");
        }
        isUsed = true;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return discountAmount;
    }
}
