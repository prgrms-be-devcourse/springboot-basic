package com.programmers.springbasic.repository.dto.voucher;

import com.programmers.springbasic.entity.voucher.VoucherType;

import jakarta.validation.constraints.Min;

public record CreateVoucherRequest(
	VoucherType voucherType,
	@Min(0) long discountValue
) {
}

