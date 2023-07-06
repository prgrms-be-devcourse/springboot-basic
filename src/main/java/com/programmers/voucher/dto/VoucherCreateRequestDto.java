package com.programmers.voucher.dto;

import com.programmers.voucher.domain.VoucherType;

public record VoucherCreateRequestDto(String name, long amount, VoucherType type) {

}
