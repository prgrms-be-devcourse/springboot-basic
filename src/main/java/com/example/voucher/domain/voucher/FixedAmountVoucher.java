package com.example.voucher.domain.voucher;


import java.time.LocalDateTime;
import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static java.time.LocalDateTime.now;

public class FixedAmountVoucher extends Voucher {

	public FixedAmountVoucher(Long voucherId, int discountAmount) {
		super(FIXED_AMOUNT_VOUCHER, voucherId, discountAmount, now(), null);
		validateDiscountAmount(discountAmount);
	}

	public FixedAmountVoucher(Long voucherId, int discountAmount, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super(FIXED_AMOUNT_VOUCHER, voucherId, discountAmount, createdAt, updatedAt);
		validateDiscountAmount(discountAmount);
	}

	private void validateDiscountAmount(int discountAmount) {
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
