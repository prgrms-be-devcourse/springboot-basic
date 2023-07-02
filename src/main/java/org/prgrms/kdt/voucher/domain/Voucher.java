package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final DiscountPolicy discountPolicy;

    public Voucher(VoucherType voucherType, DiscountPolicy discountPolicy) {
        this.voucherId = UUID.randomUUID();
        this.voucherType = voucherType;
        this.discountPolicy = discountPolicy;
    }

    public Voucher(UUID voucherId, VoucherType voucherType, DiscountPolicy discountPolicy) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountPolicy = discountPolicy;
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

    public double discount(double originPrice) {
        return discountPolicy.applyDiscount(originPrice);
    }
}
