package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

import com.programmers.springbasic.enums.ErrorCode;

public class PercentDiscountVoucher extends Voucher {

	private static final long MINIMUM_PERCENT = 0;
	private static final long MAXIMUM_PERCENT = 100;

	private final long percent;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		super(voucherId, VoucherType.PERCENT_DISCOUNT);
		if (percent < MINIMUM_PERCENT || percent > MAXIMUM_PERCENT) {
			throw new IllegalArgumentException(ErrorCode.PERCENT_OUT_OF_RANGE.getMessage());
		}
		this.percent = percent;
	}

	@Override
	public long getDiscountValue() {
		return percent;
	}

}
