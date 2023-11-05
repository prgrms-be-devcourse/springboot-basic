package com.prgrms.vouchermanagement.core.voucher.controller.request;

public class VoucherCreationRequest {

    private final String name;
    private final String voucherType;
    private final long amount;

    public VoucherCreationRequest(String name, String voucherType, long amount) {
        this.name = name;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }
}
