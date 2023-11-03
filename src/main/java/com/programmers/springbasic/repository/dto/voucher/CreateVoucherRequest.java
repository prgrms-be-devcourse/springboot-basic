package com.programmers.springbasic.repository.dto.voucher;

import com.programmers.springbasic.entity.voucher.VoucherType;

public record CreateVoucherRequest(VoucherType voucherType, long discountValue) {
}
