package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public class PercentDiscountVoucher extends Voucher {

	public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
		super(VoucherType.PERCENT, voucherId, createdAt, new PercentDiscounter(), percent);

		if (percent <= 0 || percent > 100) {
			throw new IllegalArgumentException("PercentDiscountVoucher - 퍼센트는 0과 100 사이여야 합니다.");
		}
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
			"percent=" + this.getDiscountInfo() +
			'}';
	}
}
