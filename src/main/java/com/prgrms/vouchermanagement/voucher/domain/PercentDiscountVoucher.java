package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public class PercentDiscountVoucher extends Voucher {
	private final long percent;

	public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
		super(VoucherType.PERCENT, voucherId, createdAt);

		if (percent <= 0 || percent > 100) {
			throw new IllegalArgumentException("PercentDiscountVoucher - 퍼센트는 0과 100 사이여야 합니다.");
		}
		this.percent = percent;
	}

	@Override
	public long getDiscountInfo() {
		return percent;
	}

	@Override
	public long discount(long beforeDiscount) {
		return beforeDiscount * (100 - percent / 100);
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
			"percent=" + percent +
			'}';
	}
}
