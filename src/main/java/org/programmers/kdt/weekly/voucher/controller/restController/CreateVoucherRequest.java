package org.programmers.kdt.weekly.voucher.controller.restController;

import org.programmers.kdt.weekly.voucher.model.VoucherType;

public record CreateVoucherRequest(VoucherType voucherType, int value) {

	public CreateVoucherRequest(VoucherType voucherType, int value) {
		this.voucherType = voucherType;
		this.value = value;
	}
}
