package com.programmers.springbasic.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;

public class CreatePercentDiscountVoucherRequest {

	private final long percent;

	public CreatePercentDiscountVoucherRequest(long percent) {
		this.percent = percent;
	}

	public PercentDiscountVoucher toEntity(UUID voucherId) {
		return new PercentDiscountVoucher(voucherId, percent);
	}
}
