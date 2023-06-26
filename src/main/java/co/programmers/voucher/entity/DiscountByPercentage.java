package co.programmers.voucher.entity;

public class DiscountByPercentage implements DiscountStrategy {
	private static final int MAX_AMOUNT = 100;
	private static final String DISCOUNT_TYPE_NAME = "Discount by percentage";
	private final int amount;

	public DiscountByPercentage(int amount) {
		validate(amount);
		this.amount = amount;
	}

	private static void validate(Integer amount) throws IllegalArgumentException {
		if (amount <= 0 || amount >= MAX_AMOUNT) {
			throw new IllegalArgumentException("Amount must be between 0 and 100");
		}
	}

	@Override
	public int discount(int originalPrice) {
		return originalPrice * (1 - amount / 100);
	}

	public String getType() {
		return DISCOUNT_TYPE_NAME;
	}
}
