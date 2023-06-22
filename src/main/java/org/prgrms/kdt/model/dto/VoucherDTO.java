package org.prgrms.kdt.model.dto;

import org.prgrms.kdt.enums.VoucherType;

public class VoucherDTO {

	private final Long voucherId;

	private final int amount;

	private final VoucherType voucherType;

	public VoucherDTO(int amount, VoucherType voucherType) {
		this(null, amount, voucherType);
	}

	public VoucherDTO(Long voucherId, int amount, VoucherType voucherType) {
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
