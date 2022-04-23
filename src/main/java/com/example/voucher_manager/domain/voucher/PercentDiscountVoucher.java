package com.example.voucher_manager.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final Long discountPercent;
    private final VoucherType voucherType;
    private UUID ownerId;

    public PercentDiscountVoucher(UUID voucherId, Long discountPercent, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
        this.voucherType = voucherType;
        this.ownerId = null; // 주인 미배정 상태
    }

    public PercentDiscountVoucher(UUID voucherId, Long discountPercent, VoucherType voucherType, UUID ownerId) {
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
        this.voucherType = voucherType;
        this.ownerId = ownerId;
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
    public void provideToCustomer(UUID ownerId) {
        this.ownerId = this.ownerId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public Long getDiscountInformation() {
        return discountPercent;
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }
}
