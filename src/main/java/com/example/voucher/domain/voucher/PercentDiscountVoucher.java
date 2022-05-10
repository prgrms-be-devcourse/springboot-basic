package com.example.voucher.domain.voucher;

import java.time.LocalDateTime;
import static com.example.voucher.domain.voucher.VoucherType.PERCENT_DISCOUNT_VOUCHER;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static java.time.LocalDateTime.now;

public class PercentDiscountVoucher extends Voucher {

	public PercentDiscountVoucher(Long voucherId, int discountAmount) {
		super(PERCENT_DISCOUNT_VOUCHER, voucherId, discountAmount, now(), null);

		validateDiscountAmount(discountAmount);
	}

	public PercentDiscountVoucher(Long voucherId, int discountAmount, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super(PERCENT_DISCOUNT_VOUCHER, voucherId, discountAmount, createdAt, updatedAt);
	}

	private void validateDiscountAmount(int discountAmount) {
		if (discountAmount < 0 || discountAmount > 100) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(INVALID_INPUT.getMessage());
		}
	}

	@Override
	public int discount(int beforeDiscount) {
		double percentDiscountValue = beforeDiscount * (discountAmount / 100.0);
		return (int)(beforeDiscount - percentDiscountValue);
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher," +
				voucherId + "," +
				discountAmount;
	}
}
