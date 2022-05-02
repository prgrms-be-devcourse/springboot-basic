package org.prgms.kdtspringvoucher.voucher.dto;

public class UpdateVoucherRequest {
    private Long amount;

    public UpdateVoucherRequest(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
