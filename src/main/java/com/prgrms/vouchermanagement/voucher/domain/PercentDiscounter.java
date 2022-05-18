package com.prgrms.vouchermanagement.voucher.domain;

public class PercentDiscounter implements Discountable {
	private static int MIN_PERCENT = 0;
	private static int MAX_PERCENT = 100;

	@Override
	public long evaluate(long percent, long beforeDiscount) {
		if (percent <= MIN_PERCENT || percent > MAX_PERCENT) {
			throw new IllegalArgumentException("PercentDiscounter - 퍼센트는 0과 100 사이여야 합니다.");
		}

		return beforeDiscount * (100 - percent / 100);
	}
}
