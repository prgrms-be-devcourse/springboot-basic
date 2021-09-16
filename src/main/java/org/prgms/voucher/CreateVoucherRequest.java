package org.prgms.voucher;

public class CreateVoucherRequest {
    private Long amount;

    private VoucherType voucherType;

    public Long getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

}
