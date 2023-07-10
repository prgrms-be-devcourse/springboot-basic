package org.prgrms.application.controller.voucher.request;

public class VoucherDeleteRequest {
    private String voucherId;

    public VoucherDeleteRequest(String voucherId) {
        this.voucherId = voucherId;
    }

    public Long getVoucherId() {
        return voucherId;
    }
}
