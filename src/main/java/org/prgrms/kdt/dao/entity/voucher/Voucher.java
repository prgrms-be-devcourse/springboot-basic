package org.prgrms.kdt.dao.entity.voucher;

import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final double discountAmount;
    private final String voucherType;

    public Voucher(UUID voucherId, String discountAmount, String voucherType) {
        VoucherType.of(voucherType).validate(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = Double.parseDouble(discountAmount);
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                ", voucherType='" + voucherType + '\'' +
                '}';
    }
}
