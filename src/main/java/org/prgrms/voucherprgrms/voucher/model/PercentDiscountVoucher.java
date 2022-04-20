package org.prgrms.voucherprgrms.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final String DTYPE = "PercentDiscountVoucher";

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return percent;
    }

    public String getDTYPE() {
        return DTYPE;
    }

    @Override
    public String toString() {
        return VoucherType.PERCENTDISCOUNT.getName();
    }
}
