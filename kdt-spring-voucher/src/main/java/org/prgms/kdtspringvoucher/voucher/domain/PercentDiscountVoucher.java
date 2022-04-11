package org.prgms.kdtspringvoucher.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private UUID voucherId;
    private Long percent;

    public PercentDiscountVoucher(UUID voucherId, Long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherID() {
        return voucherId;
    }

    @Override
    public Long getAmount() {
        return percent;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher " + voucherId + " " + percent;
    }
}
