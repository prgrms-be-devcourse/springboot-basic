package org.prgrms.kdt.model.entity;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;

public class VoucherEntity {
	private Long voucherId;

	private Amount amount;

	private VoucherType voucherType;

	public VoucherEntity(Amount amount, VoucherType voucherType) {
		this(null, amount, voucherType);
	}

	public VoucherEntity(Long voucherId, Amount amount, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
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
