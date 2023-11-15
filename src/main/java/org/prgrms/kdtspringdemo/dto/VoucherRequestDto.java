package org.prgrms.kdtspringdemo.dto;

public class VoucherRequestDto {
    private String voucherPolicy;
    private Long amount;

    public VoucherRequestDto(String voucherPolicy, long amount) {
        this.voucherPolicy = voucherPolicy;
        this.amount = amount;
    }

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
