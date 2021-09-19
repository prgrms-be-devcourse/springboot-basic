package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private long discount;
    private final LocalDateTime createdAt;
    private VoucherType voucherType;

    public Voucher(UUID voucherId, long discount, LocalDateTime createdAt,
        VoucherType voucherType) {
        validateDiscount(voucherType.getDiscountPolicy(), discount);
        this.voucherId = voucherId;
        this.discount = discount;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
    }

    public void validateDiscount(DiscountPolicy discountPolicy, long discount) {
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

    public long discount(long beforeDiscount) {
        return voucherType.getDiscountPolicy().discount(beforeDiscount, discount);
    }


    public void changeVoucherType(VoucherType voucherType, long discount) {
        validateDiscount(voucherType.getDiscountPolicy(), discount);
        this.voucherType = voucherType;
        this.discount = discount;
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
