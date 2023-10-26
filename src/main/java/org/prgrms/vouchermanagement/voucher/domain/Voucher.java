package org.prgrms.vouchermanagement.voucher.domain;

import org.prgrms.vouchermanagement.voucher.policy.DiscountPolicy;

import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final DiscountPolicy discountPolicy;

    public Voucher(UUID voucherId, DiscountPolicy discountPolicy) {
        this.voucherId = voucherId;
        this.discountPolicy = discountPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }
}
