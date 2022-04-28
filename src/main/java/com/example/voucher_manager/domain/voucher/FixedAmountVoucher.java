package com.example.voucher_manager.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final Long discountPrice;
    private final VoucherType voucherType;

    private FixedAmountVoucher(UUID voucherId, Long discountPrice, VoucherType voucherType) {
        super(voucherId, null, null);
        this.discountPrice = discountPrice;
        this.voucherType = voucherType;
    }

    private FixedAmountVoucher(UUID voucherId, Long discountPrice, VoucherType voucherType, UUID ownerId) {
        super(voucherId, ownerId, null);
        this.discountPrice = discountPrice;
        this.voucherType = voucherType;
    }

    private FixedAmountVoucher(UUID voucherId, Long discountPrice, VoucherType voucherType, UUID ownerId, LocalDateTime createdAt) {
        super(voucherId, ownerId, createdAt);
        this.discountPrice = discountPrice;
        this.voucherType = voucherType;
    }

    public static FixedAmountVoucher of(UUID voucherId, Long discountPrice, VoucherType voucherType) {
        return new FixedAmountVoucher(voucherId, discountPrice, voucherType);
    }

    public static FixedAmountVoucher of(UUID voucherId, Long discountPrice, VoucherType voucherType, UUID ownerId) {
        return new FixedAmountVoucher(voucherId, discountPrice, voucherType, ownerId);
    }

    public static FixedAmountVoucher of(UUID voucherId, Long discountPrice, VoucherType voucherType, UUID ownerId, LocalDateTime createdAt) {
        return new FixedAmountVoucher(voucherId, discountPrice, voucherType, ownerId, createdAt);
    }

    public static FixedAmountVoucher of(VoucherDto voucherDto) {
        return new FixedAmountVoucher(voucherDto.getVoucherId(),
                voucherDto.getDiscountInfo(),
                voucherDto.getVoucherType(),
                voucherDto.getOwnerId(),
                voucherDto.getCreatedAt());
    }

    @Override
    public String toString() {
        return "[VoucherType : FixedAmountVoucher," + " VoucherId : " + voucherId + ", DiscountPrice : " + discountPrice + "$]";
    }

    @Override
    public Long discount(Long regularPrice) {
        return regularPrice - discountPrice;
    }

    @Override
    public void provideToCustomer(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public Long getDiscountInformation() {
        return discountPrice;
    }

}
