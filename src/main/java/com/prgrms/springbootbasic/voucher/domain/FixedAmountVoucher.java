package com.prgrms.springbootbasic.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final String voucherType;
    private final int fixedAmount;

    public FixedAmountVoucher(String voucherType, int fixedAmount) {
        this.id = UUID.randomUUID();
        this.voucherType = voucherType;
        this.fixedAmount = fixedAmount;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }

    @Override
    public UUID getUUID() {
        return id;
    }

    @Override
    public int getDiscountRate() {
        return fixedAmount;
    }
}
