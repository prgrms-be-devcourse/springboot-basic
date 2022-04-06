package org.prgms.voucheradmin.domain.voucher.entity;

import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.PERCENTAGE_DISCOUNT;

import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

public class PercentageDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long percent;

    public PercentageDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.voucherType = PERCENTAGE_DISCOUNT;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getPercent() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (beforeDiscount * (percent / 100));
    }

    @Override
    public String toString() {
        StringBuilder voucherInfoBuilder = new StringBuilder();
        voucherInfoBuilder.append(voucherId).append("\t").append(voucherType).append("\t").append(percent);
        return voucherInfoBuilder.toString();
    }
}
