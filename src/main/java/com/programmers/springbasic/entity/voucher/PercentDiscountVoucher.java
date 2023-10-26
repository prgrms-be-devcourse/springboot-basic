package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

import com.programmers.springbasic.constants.ErrorCode;

public class PercentDiscountVoucher extends Voucher {

	private static final long MINIMUM_PERCENT = 0;
	private static final long MAXIMUM_PERCENT = 100;

	private long percent;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		super(voucherId, VoucherType.PERCENT_DISCOUNT);
		validateDiscountValue(percent);
		this.percent = percent;
	}

	@Override
	public long getDiscountValue() {
		return percent;
	}

	@Override
	public void changeDiscountValue(long newDiscountValue) {
		validateDiscountValue(newDiscountValue);
		this.percent = newDiscountValue;
	}

	@Override
	public void validateDiscountValue(long discountValue) {
		if (discountValue < MINIMUM_PERCENT || discountValue > MAXIMUM_PERCENT) {
			throw new IllegalArgumentException(ErrorCode.PERCENT_OUT_OF_RANGE.getMessage());
		}
	}

}
