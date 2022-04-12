package com.prgrms.management.voucher.domain;

public class VoucherRequest {
    private String voucherType;
    private String amount;

    public VoucherRequest(String voucherType, String amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String getAmount() {
        return amount;
    }
}
