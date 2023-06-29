package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.domain.enums.VoucherType;
import com.example.voucher.utils.validator.VoucherValidator;

public class FixedAmountVoucher implements Voucher {

	private final UUID voucherId;
	private final long amount;

	VoucherType voucherType = VoucherType.FixedAmount;

	public FixedAmountVoucher(UUID voucherId, long amount) {
		this.voucherId = voucherId;
		this.amount = amount;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public long discount(long beforeAmount) {
		VoucherValidator.validateNonZero(beforeAmount);
		VoucherValidator.validatePositive(beforeAmount);
		VoucherValidator.validateGreaterThan(beforeAmount, amount);

		return beforeAmount - amount;
	}

	@Override
	public String getInfo() {
		return String.format("VoucherType : %s, discountAmount : %d", voucherType.getTypeName(), amount);
	}

}
