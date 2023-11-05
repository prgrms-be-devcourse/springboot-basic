package org.programmers.springorder.voucher.dto;

import org.programmers.springorder.voucher.model.VoucherType;

public record VoucherRequestDto(long discountValue, VoucherType voucherType) {
}
