package com.dev.voucherproject.model.voucher;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Voucher {
    private UUID voucherId;
    private LocalDateTime createdAt;

    public Voucher(UUID voucherId, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract long discount(long beforeDiscount);

    public abstract long getDiscountFigure();

    public String getPolicyName() {
        return this.getClass().getSimpleName();
    };

    public static Voucher of(UUID uuid, VoucherPolicy voucherPolicy, long discountFigure) {
        LocalDateTime createdAt = LocalDateTime.now();

        if (voucherPolicy == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            return new FixedAmountVoucher(uuid, createdAt, discountFigure);
        }

        return new PercentDiscountVoucher(uuid, createdAt, discountFigure);
    }

    public static Voucher of(UUID uuid, LocalDateTime createdAt, VoucherPolicy voucherPolicy, long discountFigure) {
        if (voucherPolicy == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            return new FixedAmountVoucher(uuid, createdAt, discountFigure);
        }

        return new PercentDiscountVoucher(uuid, createdAt, discountFigure);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherId, voucher.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}
