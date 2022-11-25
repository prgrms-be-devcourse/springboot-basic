package com.programmers.voucher.controller.dto;

public record VoucherCreateRequest(String voucherType, long discountValue) {
}
