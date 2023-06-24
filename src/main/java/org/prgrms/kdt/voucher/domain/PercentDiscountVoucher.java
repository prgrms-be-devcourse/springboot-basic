package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final String voucherType = "PercentDiscountVoucher";
    private final long percent = 20;

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
    public Long discount(Long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}
