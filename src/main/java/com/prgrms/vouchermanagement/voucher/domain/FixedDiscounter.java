package com.prgrms.vouchermanagement.voucher.domain;

public class FixedDiscounter implements Discountable {
	private static long MIN_AMOUNT = 0L;

	@Override
	public long evaluate(long amount, long beforeDiscount) {
		if (amount <= MIN_AMOUNT) {
			throw new IllegalArgumentException("FixedDiscounter : Amount 는 양수여야 합니다");
		}
		long discountedAmount = beforeDiscount - amount;

		return (discountedAmount < 0) ? 0 : discountedAmount;
	}
}
