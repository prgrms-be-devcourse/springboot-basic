package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

// enum && using enum
public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final String voucherType = "PercentDiscountVoucher";
    private final long amount = 20;

    public PercentDiscountVoucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }
    @Override
    public long getAmount() {
        return amount;
    }
    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount * (amount / 100);
    }
}
