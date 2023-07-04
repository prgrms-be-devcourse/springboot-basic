package com.programmers.domain.voucher.dto;

import com.programmers.domain.voucher.VoucherType;

public record VoucherCreateRequestDto(String name, long amount, VoucherType type) {

}
