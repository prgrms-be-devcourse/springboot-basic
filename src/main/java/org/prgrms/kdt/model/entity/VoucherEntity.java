package org.prgrms.kdt.model.entity;

import org.prgrms.kdt.enums.VoucherType;

public class VoucherEntity {

	private Long voucherId;

	private int amount;

	private VoucherType voucherType;

	public VoucherEntity() {
	}

	public VoucherEntity(Long voucherId, int amount, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public int getAmount() {
		return amount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}
}
