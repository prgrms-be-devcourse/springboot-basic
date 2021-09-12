package org.prgrms.kdt.controller.voucher;

public class VoucherRequest {

    private Long voucherId;
    private String voucherType;
    private long amount;
    private Long customerId;

    public Long getVoucherId() {
        return voucherId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
