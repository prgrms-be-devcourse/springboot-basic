package org.prgrms.kdt.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.model.discount.DiscountStrategy;

public class Voucher {

    private final UUID voucherId;
    private final long discount;
    private final LocalDateTime createdAt;

    private VoucherType voucherType;
    private DiscountStrategy discountStrategy;

    public Voucher(UUID voucherId, long discount, LocalDateTime createdAt,
        VoucherType voucherType, DiscountStrategy discountStrategy) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
        this.discountStrategy = discountStrategy;
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

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public long discount(long beforeDiscount) {
        return discountStrategy.discount(beforeDiscount, discount);
    }

    public void changeVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
        this.discountStrategy = voucherType.getDiscountStrategy();
    }

    @Override
    public String toString() {
        return "Voucher{" +
            "voucherId=" + voucherId +
            ", discount=" + discount +
            ", createdAt=" + createdAt +
            ", voucherType=" + voucherType +
            '}';
    }
}
