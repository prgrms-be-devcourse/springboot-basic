package com.prgrms.springbootbasic.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    UUID id;
    String voucherType;
    int fixedAmount;

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
