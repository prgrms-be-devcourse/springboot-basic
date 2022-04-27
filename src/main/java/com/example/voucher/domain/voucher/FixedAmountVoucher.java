package com.example.voucher.domain.voucher;


import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;

public class FixedAmountVoucher extends Voucher {

	public FixedAmountVoucher(Long voucherId, int discountAmount) {
		super(voucherId, discountAmount);

		if (discountAmount < 0) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(INVALID_INPUT.name());
		}
	}

	@Override
	public int discount(int beforeDiscount) {
		if (beforeDiscount < discountAmount) {
			return 0;
		}
		return beforeDiscount - discountAmount;

	}

	@Override
	public String toString() {
		return "FixedAmountVoucher," +
				voucherId + "," +
				discountAmount;
	}
}
