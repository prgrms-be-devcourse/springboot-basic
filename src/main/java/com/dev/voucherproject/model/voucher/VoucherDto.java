package com.dev.voucherproject.model.voucher;


import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final VoucherPolicy voucherPolicy;
    private final UUID voucherId;
    private final long discountFigure;
    private final LocalDateTime createdAt;

    private VoucherDto(VoucherPolicy voucherPolicy, UUID voucherId, long discountFigure, LocalDateTime createdAt) {
        this.voucherPolicy = voucherPolicy;
        this.voucherId = voucherId;
        this.discountFigure = discountFigure;
        this.createdAt = createdAt;
    }
    
    public static VoucherDto fromEntity(Voucher voucher) {
        return new VoucherDto(VoucherPolicy.convertPolicyNameToPolicy(voucher.getPolicyName()), voucher.getVoucherId(), voucher.getDiscountFigure(), voucher.getCreatedAt());
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountFigure() {
        return discountFigure;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
