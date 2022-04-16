package com.mountain.voucherApp.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherEntity {
    private final UUID voucherId;
    private final Integer discountPolicyId; // fk
    private final Long discountAmount;

    public VoucherEntity(UUID voucherId, Integer discountPolicyId, Long discountAmount) {
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
