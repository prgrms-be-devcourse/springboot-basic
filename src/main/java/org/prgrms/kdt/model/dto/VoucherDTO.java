package org.prgrms.kdt.model.dto;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;

public class VoucherDTO {

	private final Long voucherId;

	private final Amount amount;

	private final VoucherType voucherType;

	public VoucherDTO(Amount amount, VoucherType voucherType) {
		this(null, amount, voucherType);
	}

	public VoucherDTO(Long voucherId, Amount amount, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public static VoucherDTO of(Amount amount, VoucherType voucherType) {
		return new VoucherDTO(amount, voucherType);
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
