package org.prgrms.kdt.model.entity;

import org.prgrms.kdt.enums.VoucherType;

public class VoucherEntity {
	private final Long voucherId;

	private final Amount amount;

	private final VoucherType voucherType;

	public VoucherEntity(Amount amount, VoucherType voucherType) {
		this(null, amount, voucherType);
	}

	public VoucherEntity(Long voucherId, Amount amount, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public Amount getAmount() {
		return amount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}
}
