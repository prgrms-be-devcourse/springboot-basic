package org.prgrms.kdtspringdemo.voucher.domain.dto;

import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;

import java.util.UUID;

public class VoucherRequestDto {
    private String voucherPolicy;
    private Long amount;
    private Long percentage;
    public String getVoucherPolicy() {
        return voucherPolicy;
    }
    public Long getAmount() {
        return amount;
    }

    public Long getPercentage() {
        return percentage;
    }
}
