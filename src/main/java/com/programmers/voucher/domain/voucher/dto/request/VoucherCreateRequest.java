package com.programmers.voucher.domain.voucher.dto.request;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.validation.VoucherTypeAmountValidation;
import jakarta.validation.constraints.NotNull;

@VoucherTypeAmountValidation
public class VoucherCreateRequest {
    private static final String TYPE_NOT_NULL = "Voucher type must not be null";
    private static final String AMOUNT_NOT_NULL = "Voucher amount must not be null";

    @NotNull(message = TYPE_NOT_NULL)
    private VoucherType voucherType;
    @NotNull(message = AMOUNT_NOT_NULL)
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
