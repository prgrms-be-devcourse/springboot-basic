package org.prgrms.voucherprgrms.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;

    private final String DTYPE = "FixedAmountVoucher";

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return amount;
    }

    public String getDTYPE() {
        return DTYPE;
    }


    @Override
    public String toString() {
        return VoucherType.FIXEDAMOUNT.getName();
    }
}
