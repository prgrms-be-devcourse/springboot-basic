package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

	private final long percent;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		super(voucherId, VoucherType.PERCENT_DISCOUNT);
		this.percent = percent;
	}

	@Override
	public long getDiscountValue() {
		return percent;
	}

}
