package org.prgrms.kdtspringdemo.voucher.domain.dto;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;

import java.util.UUID;

public class VoucherViewDto {
    private UUID voucherId;
    private String voucherPolicy;
    private Long amount;

    public VoucherViewDto(UUID voucherId, String voucherPolicy, Long amount) {
        this.voucherId = voucherId;
        this.voucherPolicy = voucherPolicy;
        this.amount = amount;
    }

    public VoucherViewDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherPolicy = voucher.getVoucherPolicy().getVoucherType();
        this.amount = voucher.getVoucherPolicy().getAmount();
    }

    @Override
    public String toString() {
        return "voucherId =" + voucherId +
                " / voucherPolicy ='" + voucherPolicy + '\'' +
                " / amount =" + amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
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
