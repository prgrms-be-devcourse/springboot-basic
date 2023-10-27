package com.programmers.vouchermanagement.voucher.dto;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public record CreateVoucherRequest(long discountValue, VoucherType voucherType) {
}
