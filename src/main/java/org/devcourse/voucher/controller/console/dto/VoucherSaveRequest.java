package org.devcourse.voucher.controller.console.dto;

import org.devcourse.voucher.domain.voucher.VoucherType;

public record VoucherSaveRequest(VoucherType voucherType, int amount) {

    public VoucherSaveRequest(String voucherType, String amount) {
        this(VoucherType.find(voucherType), Integer.parseInt(amount));
    }
}
