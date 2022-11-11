package com.prgrms.springbootbasic.voucher.domain;

import java.util.UUID;

public class PercentVoucher implements Voucher {

    UUID id;
    String voucherType;
    int percent;

    public PercentVoucher(String voucherType, int percent) {
        this.id = UUID.randomUUID();
        this.voucherType = voucherType;
        this.percent = percent;
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
        return percent;
    }
}
