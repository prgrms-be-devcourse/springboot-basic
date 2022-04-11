package org.prgms.kdtspringvoucher.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private UUID voucherId;
    private Long amount;

    public FixedAmountVoucher(UUID voucherId, Long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherID() {
        return voucherId;
    }

    @Override
    public Long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher " + voucherId + " " + amount;
    }
}
