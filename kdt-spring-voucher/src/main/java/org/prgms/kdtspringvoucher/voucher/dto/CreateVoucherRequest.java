package org.prgms.kdtspringvoucher.voucher.dto;

import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;

public class CreateVoucherRequest {
    private VoucherType voucherType;
    private Long amount;

    public CreateVoucherRequest(VoucherType voucherType, Long amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
