package com.example.voucher_manager.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long discountPrice;
    private final VoucherType voucherType;
    private UUID ownerId;

    public FixedAmountVoucher(UUID voucherId, Long discountPrice, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
        this.voucherType = voucherType;
        this.ownerId = null; // 주인 미배정 상태
    }

    public FixedAmountVoucher(UUID voucherId, Long discountPrice, VoucherType voucherType, UUID ownerId) {
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
        this.voucherType = voucherType;
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "[VoucherType : FixedAmountVoucher," + " VoucherId : " + voucherId + ", DiscountPrice : " + discountPrice + "$]";
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
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

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }
}
