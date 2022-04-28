package com.example.voucher_manager.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final Long discountInfo;
    private final VoucherType voucherType;
    private final UUID ownerId;
    private final LocalDateTime createdAt;

    private VoucherDto(UUID voucherId, Long discountInfo, VoucherType voucherType, UUID ownerId, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.discountInfo = discountInfo;
        this.voucherType = voucherType;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(),
                voucher.getDiscountInformation(),
                voucher.getVoucherType(),
                voucher.getOwnerId(),
                voucher.getCreatedAt());
    }

    public static Voucher toEntity(VoucherDto voucherDto) {
        if (voucherDto.getVoucherType().equals(VoucherType.FIXED)) {
           return FixedAmountVoucher.of(voucherDto);
        }
        return PercentDiscountVoucher.of(voucherDto);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getDiscountInfo() {
        return discountInfo;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
