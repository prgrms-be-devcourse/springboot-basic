package com.programmers.springbasic.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.enums.ErrorCode;

public class CreatePercentDiscountVoucherRequest {

	private static final long MINIMUM_PERCENT = 0;
	private static final long MAXIMUM_PERCENT = 100;

	private final long percent;

	public CreatePercentDiscountVoucherRequest(long percent) {
		if (percent < MINIMUM_PERCENT || percent > MAXIMUM_PERCENT) {
			throw new IllegalArgumentException(ErrorCode.PERCENT_OUT_OF_RANGE.getMessage());
		}
		this.percent = percent;
	}

	public PercentDiscountVoucher toEntity(UUID voucherId) {
		return new PercentDiscountVoucher(voucherId, percent);
	}
}
