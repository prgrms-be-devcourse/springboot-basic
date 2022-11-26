package org.prgrms.springorder.controller.dto;

import org.prgrms.springorder.domain.voucher.VoucherType;

public class VoucherRequestDto {

	private VoucherType voucherType;

	private double value;

	public VoucherRequestDto(VoucherType voucherType, double value) {
		this.voucherType = voucherType;
		this.value = value;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public double getValue() {
		return value;
	}
}
