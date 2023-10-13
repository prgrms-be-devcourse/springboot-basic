package study.dev.spring.voucher.domain;

import lombok.RequiredArgsConstructor;
import study.dev.spring.voucher.domain.discount.Discounter;
import study.dev.spring.voucher.domain.discount.FixedDiscounter;
import study.dev.spring.voucher.domain.discount.PercentDiscounter;

@RequiredArgsConstructor
public enum VoucherType {

	FIXED("정액 할인", new FixedDiscounter()),
	PERCENT("정률 할인", new PercentDiscounter());

	private final String description;
	private final Discounter discounter;

	public double discount(
		final double price,
		final double discountAmount
	) {
		return discounter.discount(price, discountAmount);
	}
}
