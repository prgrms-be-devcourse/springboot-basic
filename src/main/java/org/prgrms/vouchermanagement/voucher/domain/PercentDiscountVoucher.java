package org.prgrms.vouchermanagement.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private final UUID voucherId;
    private final int discountAmount;
    private static final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;

    public PercentDiscountVoucher(UUID uuid, int discountAmount) {
        this.voucherId = uuid;
        this.discountAmount = discountAmount;
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
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return "바우처 종류 : " + voucherType.name() + ", ID : " + this.voucherId + ", 할인률 : " + this.discountAmount + "%";
    }
}
