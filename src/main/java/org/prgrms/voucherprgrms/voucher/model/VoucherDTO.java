package org.prgrms.voucherprgrms.voucher.model;

public class VoucherDTO {
    private String voucherType;
    private long value;

    public String getVoucherType() {
        return voucherType;
    }

    public long getValue() {
        return value;
    }

    public VoucherDTO(String voucherType, long value) {
        this.voucherType = voucherType;
        this.value = value;
    }
}
