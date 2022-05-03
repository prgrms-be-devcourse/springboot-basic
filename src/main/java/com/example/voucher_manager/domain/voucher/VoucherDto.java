package com.example.voucher_manager.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherDto(UUID voucherId, Long discountInfo,
                         VoucherType voucherType, UUID ownerId,
                         LocalDateTime createdAt) {

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
