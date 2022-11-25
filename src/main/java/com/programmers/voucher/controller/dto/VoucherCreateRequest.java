package com.programmers.voucher.controller.dto;

import com.programmers.voucher.model.voucher.VoucherType;

public record VoucherCreateRequest(VoucherType voucherType, long discountValue) {
}
