package org.prgms.vouchermanagement.voucher.domain.entity;

import org.prgms.vouchermanagement.voucher.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long returnDiscount() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
