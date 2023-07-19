package org.prgrms.kdt.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final DiscountPolicy discountPolicy;
    private final LocalDateTime createdAt;

    public Voucher(UUID voucherId, VoucherType voucherType, DiscountPolicy discountPolicy, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountPolicy = discountPolicy;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double discount(double originPrice) {
        return discountPolicy.applyDiscount(originPrice);
    }
}
