package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

import com.programmers.springbasic.enums.ErrorCode;

public class FixedAmountVoucher extends Voucher {

	private long amount;

	public FixedAmountVoucher(UUID voucherId, long amount) {
		super(voucherId, VoucherType.FIXED_AMOUNT);
		if (amount <= 0) {
			throw new IllegalArgumentException(ErrorCode.AMOUNT_SHOULD_BE_POSITIVE.getMessage());
		}
		this.amount = amount;
	}

	@Override
	public long getDiscountValue() {
		return amount;
	}

	@Override
	public void changeDiscountValue(long newDiscountValue) {
		//todo : validation
		this.amount = newDiscountValue;
	}

}
