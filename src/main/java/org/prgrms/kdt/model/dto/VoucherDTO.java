package org.prgrms.kdt.model.dto;

import java.util.Objects;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;

public class VoucherDTO {

	private final Long voucherId;

	private final Amount amount;

	private final VoucherType voucherType;

	public VoucherDTO(Long voucherId, Amount amount, VoucherType voucherType) {
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
