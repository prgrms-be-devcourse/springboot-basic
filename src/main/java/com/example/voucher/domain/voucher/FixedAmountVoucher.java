package com.example.voucher.domain.voucher;


import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;

public class FixedAmountVoucher extends Voucher {

	public FixedAmountVoucher(int discountAmount) {
		if (discountAmount < 0) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(INVALID_INPUT.name());
		}
		this.discountAmount = discountAmount;
	}

	@Override
	public int discount(int beforeDiscount) {
		if (beforeDiscount >= discountAmount) {
			return beforeDiscount - discountAmount;
		}
		return 0;
	}
}
