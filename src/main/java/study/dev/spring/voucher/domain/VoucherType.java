package study.dev.spring.voucher.domain;

import static study.dev.spring.voucher.exception.VoucherErrorCode.*;

import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import study.dev.spring.voucher.domain.discount.Discounter;
import study.dev.spring.voucher.domain.discount.FixedDiscounter;
import study.dev.spring.voucher.domain.discount.PercentDiscounter;
import study.dev.spring.voucher.exception.VoucherException;

@RequiredArgsConstructor
public enum VoucherType {

	FIXED(
		"정액 할인",
		new FixedDiscounter(),
		discountAmount -> {
			if (discountAmount < 0) {
				throw new VoucherException(NEGATIVE_DISCOUNT_AMOUNT);
			}
		}),
	PERCENT(
		"정률 할인",
		new PercentDiscounter(),
		discountAmount -> {
			if (discountAmount < 0 || discountAmount > 100) {
				throw new VoucherException(INVALID_RANGE_DISCOUNT_AMOUNT);
			}
		}
	);

	private final String description;
	private final Discounter discounter;
	private final Consumer<Double> validator;

	public double discount(
		final double price,
		final double discountAmount
	) {
		return discounter.discount(price, discountAmount);
	}

	public void validateDiscountAmount(final double discountAmount) {
		validator.accept(discountAmount);
	}
}
