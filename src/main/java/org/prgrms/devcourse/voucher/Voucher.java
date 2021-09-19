package org.prgrms.devcourse.voucher;

import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    private Voucher(UUID voucherId, long percent, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = percent;
        this.voucherType = voucherType;
    }

    public static Voucher of(UUID voucherId, long percent, VoucherType voucherType) {
        return new Voucher(voucherId, percent, voucherType);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return voucherType.toString() + "," + voucherId + "," + discountValue + "\n";
    }

}
