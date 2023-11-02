package org.prgrms.kdtspringdemo.voucher.domain.dto;

import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;

import java.util.UUID;

public class VoucherRequestDto {
    private String voucherPolicy;
    private Long amount;
    public String getVoucherPolicy() {
        return voucherPolicy;
    }

    public void setVoucherPolicy(String voucherPolicy) {
        this.voucherPolicy = voucherPolicy;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
