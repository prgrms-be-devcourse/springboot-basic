package com.programmers.voucher.controller.dto;

import com.programmers.voucher.model.voucher.VoucherType;

public record VoucherRequest(VoucherType voucherType, long discountValue) {
}
