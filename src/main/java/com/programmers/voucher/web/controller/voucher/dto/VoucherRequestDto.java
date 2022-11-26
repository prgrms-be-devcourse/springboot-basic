package com.programmers.voucher.web.controller.voucher.dto;

import com.programmers.voucher.domain.voucher.model.VoucherType;

public class VoucherRequestDto {

	private String discount;
	private VoucherType voucherType;

	public VoucherRequestDto() {
	}

	public VoucherRequestDto(String discount, VoucherType voucherType) {
		this.discount = discount;
		this.voucherType = voucherType;
	}

	public String getDiscount() {
		return discount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}
}
