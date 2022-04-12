package org.prgrms.voucherapplication.entity;

import java.util.UUID;

/**
 * 할인가로 적용되는 바우처 Entity
 * field: 바우처 ID, 할인가
 */
public record FixedAmountVoucher(UUID voucherID, long amount) implements Voucher {

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public String toString() {
        return "[  FixedAmountVoucher  ] ID: %32s, Amount: %3d".formatted(voucherID, amount);
    }
}
