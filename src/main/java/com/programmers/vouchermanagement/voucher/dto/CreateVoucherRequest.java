package com.programmers.vouchermanagement.voucher.dto;

import java.math.BigDecimal;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public record CreateVoucherRequest(BigDecimal discountValue, VoucherType voucherType) {
}
