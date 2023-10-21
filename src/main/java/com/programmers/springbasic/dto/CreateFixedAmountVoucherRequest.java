package com.programmers.springbasic.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;

public class CreateFixedAmountVoucherRequest {

	private final long amount;

	public CreateFixedAmountVoucherRequest(long amount) {
		this.amount = amount;
	}

	public FixedAmountVoucher toEntity(UUID voucherId) {
		return new FixedAmountVoucher(voucherId, amount);
	}
}
