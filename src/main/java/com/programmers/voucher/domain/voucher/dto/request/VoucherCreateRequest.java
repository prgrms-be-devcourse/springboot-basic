package com.programmers.voucher.domain.voucher.dto.request;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.global.validation.VoucherTypeAmountValidation;
import jakarta.validation.constraints.NotNull;

@VoucherTypeAmountValidation
public class VoucherCreateRequest {
    @NotNull
    private VoucherType voucherType;
    private long amount;

    public VoucherCreateRequest() {
    }

    public VoucherCreateRequest(VoucherType voucherType, long amount) {
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
