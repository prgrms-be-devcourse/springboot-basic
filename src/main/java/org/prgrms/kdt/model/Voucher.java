package org.prgrms.kdt.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.model.discount.DiscountStrategy;

public class Voucher {

    private final UUID voucherId;
    private final long discount;
    private final LocalDateTime createdAt;

    private boolean isUsed;
    private LocalDateTime usedAt;
    private VoucherType voucherType;
    private DiscountStrategy discountStrategy;

    public Voucher(UUID voucherId, long discount, LocalDateTime createdAt,
        VoucherType voucherType, DiscountStrategy discountStrategy) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
        this.discountStrategy = discountStrategy;
        this.isUsed = false;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public long discount(long beforeDiscount) {
        isUsed = true;
        usedAt = LocalDateTime.now();
        return discountStrategy.discount(beforeDiscount, discount);
    }

    public void changeVoucherType(VoucherType voucherType, DiscountStrategy discountStrategy) {
        if (isUsed) {
            throw new RuntimeException("already used voucher");
        }
        this.voucherType = voucherType;
        this.discountStrategy = discountStrategy;
    }

    @Override
    public String toString() {
        return "Voucher{" +
            "voucherId=" + voucherId +
            ", discount=" + discount +
            ", voucherType=" + voucherType +
            '}';
    }
}
