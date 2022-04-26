package org.prgrms.voucherapplication.entity;

import java.util.UUID;

/**
 * 할인율로 적용되는 바우처 Entity
 * field: 바우처 ID, 할인율
 */
public record PercentDiscountVoucher(UUID voucherID, long percent) implements Voucher {

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public long getDiscountValue() {
        return percent;
    }

    @Override
    public String toString() {
        return "[PercentDiscountVoucher] ID: %32s, Percent: %2d".formatted(voucherID, percent);
    }
}
