package org.prgrms.vouchermanagement.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private final UUID voucherId;
    private final int discountAmount;
    private final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
    private final UUID customerId;

    public PercentDiscountVoucher(UUID uuid, int discountAmount, UUID customerId) {
        this.voucherId = uuid;
        this.discountAmount = discountAmount;
        this.customerId = customerId;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public int getDiscountAmount() {
        return this.discountAmount;
    }

    @Override
    public UUID getCustomerId() {
        return this.customerId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return "바우처 종류 : " + voucherType.name() + ", ID : " + this.voucherId + ", 할인률 : " + this.discountAmount + "%";
    }
}
