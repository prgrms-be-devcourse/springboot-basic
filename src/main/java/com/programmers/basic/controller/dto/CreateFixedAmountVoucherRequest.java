package com.programmers.basic.controller.dto;

import java.util.UUID;

import com.programmers.basic.entity.FixedAmountVoucher;

public record CreateFixedAmountVoucherRequest(long amount) {
	public FixedAmountVoucher toEntity(UUID voucherId) {
		return new FixedAmountVoucher(voucherId, amount);
	}
}
