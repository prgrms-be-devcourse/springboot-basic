package com.example.voucher.controller;

public record VoucherCreateRequest(Integer voucherType, Long discountValue) {
}
