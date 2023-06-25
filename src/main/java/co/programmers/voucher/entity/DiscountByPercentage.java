package co.programmers.voucher.entity;

import org.springframework.stereotype.Component;

@Component
public class DiscountByPercentage implements DiscountStrategy {
	private static final DiscountByPercentage INSTANCE = new DiscountByPercentage();
	private static final int MAX_AMOUNT = 100;

	private DiscountByPercentage() {
	}

	public static DiscountByPercentage getInstance(Integer amount) {
		validate(amount);
		return INSTANCE;
	}

	private static void validate(Integer amount) throws IllegalArgumentException {
		if (amount <= 0 || amount >= MAX_AMOUNT) {
			throw new IllegalArgumentException("Amount must be between 0 and 100");
		}
	}

	@Override
	public int discount(int originalPrice, int amount) {
		return originalPrice * (1 - amount / 100);
	}
}
