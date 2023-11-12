package com.programmers.springbasic.entity.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.springbasic.constants.ErrorCode;

public class FixedAmountVoucher extends Voucher {

	private static final long MINIMUM_AMOUNT = 0;

	private long amount;

	public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
		super(voucherId, VoucherType.FIXED_AMOUNT, createdAt);
		validateDiscountValue(amount);
		this.amount = amount;
	}

	@Override
	public long getDiscountValue() {
		return amount;
	}

	@Override
	public void changeDiscountValue(long newDiscountValue) {
		validateDiscountValue(newDiscountValue);
		this.amount = newDiscountValue;
	}

	@Override
	public void validateDiscountValue(long discountValue) {
		if (discountValue <= MINIMUM_AMOUNT) {
			throw new IllegalArgumentException(ErrorCode.AMOUNT_SHOULD_BE_POSITIVE.getMessage());
		}
	}

}
