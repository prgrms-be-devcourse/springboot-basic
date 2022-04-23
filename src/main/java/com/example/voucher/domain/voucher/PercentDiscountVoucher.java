package com.example.voucher.domain.voucher;

import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;

public class PercentDiscountVoucher extends Voucher {

	public PercentDiscountVoucher(int discountAmount) {
		if (discountAmount < 0 || discountAmount > 100) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(INVALID_INPUT.name());
		}
		this.discountAmount = discountAmount;
	}

	@Override
	public int discount(int beforeDiscount) {
		double percentDiscountValue = beforeDiscount * (discountAmount / 100.0);
		return (int)(beforeDiscount - percentDiscountValue);
	}
}
