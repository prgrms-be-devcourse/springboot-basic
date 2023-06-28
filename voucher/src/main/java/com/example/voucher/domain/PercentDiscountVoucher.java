package com.example.voucher.domain;

import java.util.UUID;

import com.example.voucher.domain.enums.VoucherType;
import com.example.voucher.utils.validator.VoucherValidator;

public class PercentDiscountVoucher implements Voucher {

	private final UUID voucherId;
	private final long percent;

	private static final int PERCENT_DIVISOR = 100;

	VoucherType voucherType = VoucherType.PercentDiscount;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		VoucherValidator.validatePercent(percent);
		this.voucherId = voucherId;
		this.percent = percent;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public long discount(long beforeAmount) {
		VoucherValidator.validateNonZero(beforeAmount);

		return beforeAmount * (percent / PERCENT_DIVISOR);
	}

	@Override
	public String getInfo() {
		return String.format("VoucherType : %s, discountPercent : %d", voucherType.getTypeName(), percent);
	}

}
