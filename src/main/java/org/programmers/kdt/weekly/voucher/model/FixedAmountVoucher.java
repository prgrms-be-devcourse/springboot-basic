package org.programmers.kdt.weekly.voucher.model;

import java.util.UUID;
import lombok.Getter;

@Getter
public final class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType = VoucherType.FixedAmountVoucher;
    private final int value;

    public FixedAmountVoucher(UUID voucherId, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        this.voucherId = voucherId;
        this.value = value;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Voucher Type: " + voucherType +
            ", voucherId: " + voucherId +
            ", amount: " + value;
    }

}

