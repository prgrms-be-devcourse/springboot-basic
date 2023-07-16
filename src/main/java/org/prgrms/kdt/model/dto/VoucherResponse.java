package org.prgrms.kdt.model.dto;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;

public class VoucherResponse {

	private final Long voucherId;

	private final int amount;

	private final String voucherType;

	public VoucherResponse(Long voucherId, int amount, String voucherType) {
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

	public String getVoucherType() {
		return voucherType;
	}
}
