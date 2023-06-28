package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount = 20;

    public FixedAmountVoucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String getVoucherType() {
        return VoucherType.FIXED.getName();
    }

    @Override
    public long discount(Long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getAmount() {
        return amount;
    }
}
