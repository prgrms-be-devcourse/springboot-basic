package com.programmers.springbasic.controller.dto;

import java.util.UUID;

import com.programmers.springbasic.ErrorCode;
import com.programmers.springbasic.entity.FixedAmountVoucher;

public class CreateFixedAmountVoucherRequest {

	private final long amount;

	public CreateFixedAmountVoucherRequest(long amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(ErrorCode.AMOUNT_SHOULD_BE_POSITIVE.getMessage());
		}
		this.amount = amount;
	}

	public FixedAmountVoucher toEntity(UUID voucherId) {
		return new FixedAmountVoucher(voucherId, amount);
	}
}
