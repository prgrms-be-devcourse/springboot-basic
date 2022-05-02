package org.prgms.kdtspringvoucher.voucher.domain;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherType() {
        return voucherType;
    }
}
