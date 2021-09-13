package org.prgrms.kdt.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.model.discount.DiscountStrategy;

public class Voucher {

    private final UUID voucherId;
    private long discount;
    private final LocalDateTime createdAt;

    private VoucherType voucherType;
    private DiscountStrategy discountStrategy;

    public Voucher(UUID voucherId, long discount, LocalDateTime createdAt,
        VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
        this.discountStrategy = voucherType.getDiscountStrategy();
        validate(discount);
    }

    private void validate(long discount) {
        discountStrategy.validateDiscountAmount(discount);
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

    public void changeVoucherType(VoucherType voucherType, long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
        this.discountStrategy = voucherType.getDiscountStrategy();
        validate(discount);
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
