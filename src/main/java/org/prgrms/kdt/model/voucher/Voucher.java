package org.prgrms.kdt.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.model.discount.DiscountPolicy;

public class Voucher {

    private final UUID voucherId;
    private long discount;
    private final LocalDateTime createdAt;

    private VoucherType voucherType;
    private DiscountPolicy discountPolicy;

    public Voucher(UUID voucherId, long discount, LocalDateTime createdAt,
        VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
        this.discountPolicy = voucherType.getDiscountPolicy();
        validate(discount);
    }

    private void validate(long discount) {
        discountPolicy.validateDiscountAmount(discount);
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

    public DiscountPolicy getDiscountStrategy() {
        return discountPolicy;
    }

    public long discount(long beforeDiscount) {
        return discountPolicy.discount(beforeDiscount, discount);
    }

    public void changeVoucherType(VoucherType voucherType, long discount) {
        this.voucherType = voucherType;
        this.discount = discount;
        this.discountPolicy = voucherType.getDiscountPolicy();
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
