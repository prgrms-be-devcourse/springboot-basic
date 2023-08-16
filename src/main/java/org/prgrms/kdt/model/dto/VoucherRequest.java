package org.prgrms.kdt.model.dto;

import org.prgrms.kdt.enums.VoucherType;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VoucherRequest {
	private int amount;
	private VoucherType voucherType;

	public VoucherRequest(int amount, VoucherType voucherType) {
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public int getAmount() {
		return amount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

}
