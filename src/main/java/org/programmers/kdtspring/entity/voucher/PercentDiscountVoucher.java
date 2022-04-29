package org.programmers.kdtspring.entity.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private static final int HUNDRED = 100;

    private final int percent;
    private final String voucherType;

    public PercentDiscountVoucher(UUID voucherId, int percent, String voucherType) {
        super(voucherId);
        this.percent = percent;
        this.voucherType = voucherType;
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, int percent, String voucherType) {
        super(voucherId, customerId);
        this.percent = percent;
        this.voucherType = voucherType;
    }

    @Override
    public int getDiscount() {
        return percent;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }
}