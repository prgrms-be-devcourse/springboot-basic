package com.programmers.springbasic.controller.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.PercentDiscountVoucher;

public record CreatePercentDiscountVoucherRequest(long percent) {
	public PercentDiscountVoucher toEntity(UUID voucherId) {
		return new PercentDiscountVoucher(voucherId, percent);
	}
}
