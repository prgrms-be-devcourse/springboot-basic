package com.dev.bootbasic.voucher.dto;

import com.dev.bootbasic.voucher.domain.VoucherType;

public record VoucherDetailsResponse(VoucherType voucherType, int discountAmount) {
}
