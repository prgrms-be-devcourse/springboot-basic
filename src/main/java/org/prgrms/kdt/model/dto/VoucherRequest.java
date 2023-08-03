package org.prgrms.kdt.model.dto;

public class VoucherRequest {

	private int amount;

	private int voucherTypeIdx;

	public VoucherRequest(int amount, int voucherTypeIdx) {
		this.amount = amount;
		this.voucherTypeIdx = voucherTypeIdx;
	}

	public int getAmount() {
		return amount;
	}

	public int getVoucherTypeIdx() {
		return voucherTypeIdx;
	}
}
