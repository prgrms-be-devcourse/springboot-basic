package org.prgms.voucheradmin.domain.voucher.entity;

import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes.PERCENTAGE_DISCOUNT;

import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;

public class PercentageDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final VoucherTypes voucherTypes;
    private final long percent;

    public PercentageDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.voucherTypes = PERCENTAGE_DISCOUNT;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherTypes getVoucherType() {
        return voucherTypes;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - (beforeDiscount / 100 * percent);
    }

    @Override
    public String toString() {
        StringBuilder voucherInfoBuilder = new StringBuilder();
        voucherInfoBuilder.append(voucherId).append("\t").append(voucherTypes.getTypeName()).append("\t").append(percent);
        return voucherInfoBuilder.toString();
    }
}
