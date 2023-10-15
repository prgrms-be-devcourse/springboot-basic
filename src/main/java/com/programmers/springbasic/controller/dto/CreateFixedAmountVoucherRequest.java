package com.programmers.springbasic.controller.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.FixedAmountVoucher;

public record CreateFixedAmountVoucherRequest(long amount) {
	public FixedAmountVoucher toEntity(UUID voucherId) {
		return new FixedAmountVoucher(voucherId, amount);
	}
}
