package com.programmers.springbasic.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.enums.ErrorCode;

public class CreatePercentDiscountVoucherRequest {
	private final long percent;

	public CreatePercentDiscountVoucherRequest(long percent) {
		if (percent <= 0 || percent >= 100) {
			throw new IllegalArgumentException(ErrorCode.PERCENT_OUT_OF_RANGE.getMessage());
		}
		this.percent = percent;
	}

	public PercentDiscountVoucher toEntity(UUID voucherId) {
		return new PercentDiscountVoucher(voucherId, percent);
	}
}
