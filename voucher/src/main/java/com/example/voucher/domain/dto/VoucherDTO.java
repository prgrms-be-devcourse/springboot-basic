package com.example.voucher.domain.dto;

import java.util.UUID;

import com.example.voucher.domain.enums.VoucherType;

public record VoucherDTO(long value, VoucherType voucherType) {

}
