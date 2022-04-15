package com.prgrms.vouchermanagement.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
	private final UUID voucherId;
	private final long percent;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		if (percent <= 0 || percent > 100)
			throw new IllegalArgumentException("PercentDiscountVoucher - percent should be between 0 and 100");

		this.voucherId = voucherId;
		this.percent = percent;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public long discount(long beforeDiscount) {
		return beforeDiscount * (percent / 100);
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
			"percent=" + percent +
			'}';
	}
}
