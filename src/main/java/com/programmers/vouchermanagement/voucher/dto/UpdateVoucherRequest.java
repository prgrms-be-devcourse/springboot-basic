package com.programmers.vouchermanagement.voucher.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public record UpdateVoucherRequest(UUID voucherId, BigDecimal discountValue, VoucherType voucherType) {
}
