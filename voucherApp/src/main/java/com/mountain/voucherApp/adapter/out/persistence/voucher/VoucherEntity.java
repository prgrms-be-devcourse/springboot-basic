package com.mountain.voucherApp.adapter.out.persistence.voucher;

import com.mountain.voucherApp.shared.enums.DiscountPolicy;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherEntity {
    private final UUID voucherId;
    private Integer discountPolicyId;
    private Long discountAmount;

    public VoucherEntity(UUID voucherId, Integer discountPolicyId, Long discountAmount) {
        validatePolicyId(discountPolicyId);
        this.voucherId = voucherId;
        this.discountPolicyId = discountPolicyId;
        this.discountAmount = discountAmount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Integer getDiscountPolicyId() {
        return discountPolicyId;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    private void validatePolicyId(int discountPolicyId) {
        if (discountPolicyId <= 0 || discountPolicyId > DiscountPolicy.values().length)
            throw new RuntimeException("Name should not be blank");
    }

    public void changeVoucherInfo(Integer discountPolicyId, Long discountAmount) {
        validatePolicyId(discountPolicyId);
        this.discountAmount = discountAmount;
        this.discountPolicyId = discountPolicyId;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},{1},{2}{3}",
                voucherId,
                discountPolicyId,
                discountAmount,
                System.lineSeparator()
        );
    }
}
