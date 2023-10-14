package com.programmers.basic.controller.dto;

import java.util.UUID;

import com.programmers.basic.entity.FixedAmountVoucher;
import com.programmers.basic.entity.PercentDiscountVoucher;

public record CreatePercentDiscountVoucherRequest(long percent) {
	public PercentDiscountVoucher toEntity(UUID voucherId) {
		return new PercentDiscountVoucher(voucherId, percent);
	}
}
