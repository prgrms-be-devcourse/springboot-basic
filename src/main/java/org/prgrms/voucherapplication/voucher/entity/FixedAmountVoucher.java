package org.prgrms.voucherapplication.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher{

    public FixedAmountVoucher(UUID uuid, int discount, LocalDateTime createdAt) {
        super(uuid, discount, VoucherType.FIXED, createdAt);
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "uuid=" + uuid +
                ", discount=" + discount +
                ", voucherType=" + voucherType +
                ", createdAt=" + createdAt +
                '}';
    }
}
