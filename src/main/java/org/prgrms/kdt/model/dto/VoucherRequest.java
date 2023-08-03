package org.prgrms.kdt.model.dto;

import org.prgrms.kdt.model.domain.Voucher;
import org.prgrms.kdt.util.VoucherFactory;

public class VoucherRequest {
	private int amount;
	private String voucherType;

	public VoucherRequest(int amount, String voucherType) {
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public int getAmount() {
		return amount;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public Voucher toDto() {
		return VoucherFactory.createVoucherDomain(amount, voucherType);
	}
}
