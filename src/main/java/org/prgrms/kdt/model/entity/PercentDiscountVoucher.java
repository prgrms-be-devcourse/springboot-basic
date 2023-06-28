package org.prgrms.kdt.model.entity;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;

public class PercentDiscountVoucher implements Voucher {
	private final Long id;

	private final Amount amount;

	private final VoucherType voucherType = VoucherType.PercentDiscountVoucher;

	public PercentDiscountVoucher(Long id, Amount amount) {
		this.id = id;
		this.amount = amount;
	}

	@Override
	public Long getVoucherId() {
		return this.id;
	}

	@Override
	public Amount getAmount() {
		return this.amount;
	}

	@Override
	public VoucherType getVoucherType() {
		return this.voucherType;
	}

	@Override
	public long discount(long beforeDiscount) {
		int percent = amount.getAmount();

		return beforeDiscount * (percent / 100);
	}
}
