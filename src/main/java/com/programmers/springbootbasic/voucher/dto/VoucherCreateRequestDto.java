package com.programmers.springbootbasic.voucher.dto;

import com.programmers.springbootbasic.voucher.domain.VoucherType;

public record VoucherCreateRequestDto(String name, long value, VoucherType type) {

}
