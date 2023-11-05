package com.prgrms.vouchermanagement.core.voucher.controller.response;

public class VoucherCreateResponse {

    private String id;
    private String name;
    private long amount;
    private String voucherType;

    public VoucherCreateResponse() {
    }

    public VoucherCreateResponse(String id, String name, long amount, String voucherType) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public String getVoucherType() {
        return voucherType;
    }
}
