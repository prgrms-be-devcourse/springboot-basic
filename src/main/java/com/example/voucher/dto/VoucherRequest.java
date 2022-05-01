package com.example.voucher.dto;

import com.example.voucher.domain.voucher.VoucherType;
import javax.validation.constraints.NotNull;

public class VoucherRequest {
	@NotNull(message = "바우처 타입이 null이 될 수 없습니다.")
	private final VoucherType voucherType;

	private final int discountAmount;

	public VoucherRequest(VoucherType voucherType, int discountAmount) {
		this.voucherType = voucherType;
		this.discountAmount = discountAmount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}
}
