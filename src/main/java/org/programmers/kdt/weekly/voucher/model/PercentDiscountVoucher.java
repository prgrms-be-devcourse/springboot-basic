package org.programmers.kdt.weekly.voucher.model;


import java.util.UUID;
import lombok.Getter;

@Getter
public final class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private static final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
    private final int value;

    public PercentDiscountVoucher(UUID voucherId, int value) {
        if (value <= 0 || value >= 100) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        this.voucherId = voucherId;
        this.value = value;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (value / 100);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT_VOUCHER;
    }

    @Override
    public String toString() {
        return "Voucher Type: " + voucherType +
            ", voucherId: " + voucherId +
            ", percent: " + value + "%";
    }
}