package org.prgrms.springorder.domain;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

	public PercentDiscountVoucher(UUID voucherId, long value) {
		super(voucherId, value);
	}

	@Override
	public long discount(long beforeDiscount) {
		return 0;
	}

	@Override
	public String toString() {
		return String.format("%s,fixedAmount,%d", super.getVoucherId(), super.getValue());
	}
}
