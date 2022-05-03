package com.example.voucher_manager.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private final Long discountPercent;
    private final VoucherType voucherType;

    private PercentDiscountVoucher(UUID voucherId, Long discountPercent, VoucherType voucherType) {
        super(voucherId, null, null);
        this.discountPercent = discountPercent;
        this.voucherType = voucherType;
    }

    private PercentDiscountVoucher(UUID voucherId, Long discountPercent, VoucherType voucherType, UUID ownerId) {
        super(voucherId, ownerId, null);
        this.discountPercent = discountPercent;
        this.voucherType = voucherType;
    }

    private PercentDiscountVoucher(UUID voucherId, Long discountPercent, VoucherType voucherType, UUID ownerId, LocalDateTime createdAt) {
        super(voucherId, ownerId, createdAt);
        this.discountPercent = discountPercent;
        this.voucherType = voucherType;
    }

    public static PercentDiscountVoucher of(UUID voucherId, Long discountPercent, VoucherType voucherType) {
        return new PercentDiscountVoucher(voucherId, discountPercent, voucherType);
    }

    public static PercentDiscountVoucher of(UUID voucherId, Long discountPercent, VoucherType voucherType, UUID ownerId) {
        return new PercentDiscountVoucher(voucherId, discountPercent, voucherType, ownerId);
    }

    public static PercentDiscountVoucher of(UUID voucherId, Long discountPercent, VoucherType voucherType, UUID ownerId, LocalDateTime createdAt) {
        return new PercentDiscountVoucher(voucherId, discountPercent, voucherType, ownerId, createdAt);
    }

    public static PercentDiscountVoucher of(VoucherDto voucherDto) {
        return new PercentDiscountVoucher(voucherDto.getVoucherId(),
                voucherDto.getDiscountInfo(),
                voucherDto.getVoucherType(),
                voucherDto.getOwnerId(),
                voucherDto.getCreatedAt());
    }

    @Override
    public String toString() {
        return "[VoucherType : PercentDiscountVoucher," + " VoucherId : " + voucherId + ", DiscountPercent : " + discountPercent + "%]";
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long regularPrice) {
        return regularPrice * (1 - (discountPercent / 100L));
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public Long getDiscountInformation() {
        return discountPercent;
    }
}
