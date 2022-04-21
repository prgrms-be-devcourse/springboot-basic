package org.prgrms.voucherapp.engine.voucher.entity;

import org.prgrms.voucherapp.global.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percentAmount;

    public PercentDiscountVoucher(UUID voucherId, long percentAmount) {
        this.percentAmount = percentAmount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedPrice = (long) (beforeDiscount * (percentAmount / 100.0));
        return (discountedPrice < 0) ? 0 : discountedPrice;
    }

    @Override
    public String toString() {
        return MessageFormat.format("TYPE : {0}, AMOUNT : {1}%, ID : {2}", VoucherType.PERCENT.toString(), percentAmount, voucherId);
    }

}
