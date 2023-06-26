package com.programmers.voucher.dto;

import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.VoucherType;

public record VoucherRequestDto(Discount discount) {
}
