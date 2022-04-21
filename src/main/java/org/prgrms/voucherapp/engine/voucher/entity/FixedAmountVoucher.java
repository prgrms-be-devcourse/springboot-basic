package org.prgrms.voucherapp.engine.voucher.entity;

import org.prgrms.voucherapp.global.enums.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountAmount;
    private static final String type = VoucherType.FIX.toString();

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.discountAmount = discountAmount;
        this.voucherId = voucherId;
    }

    @Override
    public long getAmount() {
        return discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getTypeName() {
        return type;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedPrice = beforeDiscount - discountAmount;
        return (discountedPrice < 0) ? 0 : discountedPrice;
    }


    @Override
    public String toString() {
        return MessageFormat.format("TYPE : {0}, AMOUNT : {1}, ID : {2}", VoucherType.FIX.toString(), discountAmount, voucherId);
    }
}
