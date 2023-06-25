package co.programmers.voucher.entity;

import org.springframework.stereotype.Component;

@Component
public class DiscountByFixedAmount implements DiscountStrategy {
	private static final DiscountByFixedAmount INSTANCE = new DiscountByFixedAmount();
	private static final int MAX_AMOUNT = 1_000_000;

	private DiscountByFixedAmount() {
	}

	public static DiscountByFixedAmount getInstance(Integer amount) throws IllegalArgumentException {
		validate(amount);
		return INSTANCE;
	}

	private static void validate(Integer amount) throws IllegalArgumentException {
		if (amount <= 0 || amount >= MAX_AMOUNT) {
			throw new IllegalArgumentException("Amount must be between 0 and " + MAX_AMOUNT);
		}
	}

	@Override
	public int discount(int originalPrice, int amount) {
		return originalPrice - amount;
	}
}
