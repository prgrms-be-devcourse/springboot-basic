package com.programmers.springbasic.entity.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

	private final UUID voucherId;
	private final long percent;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		this.voucherId = voucherId;
		this.percent = percent;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public VoucherType getVoucherType() {
		return VoucherType.PERCENT_DISCOUNT;
	}

	@Override
	public long getAmount() {
		return 0;
	}

	@Override
	public long getPercent() {
		return percent;
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
			"voucherId=" + voucherId +
			", percent=" + percent +
			'}';
	}

}
