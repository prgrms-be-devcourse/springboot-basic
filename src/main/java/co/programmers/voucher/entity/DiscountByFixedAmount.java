package co.programmers.voucher.entity;

public class DiscountByFixedAmount implements DiscountStrategy {
	private static final int MAX_AMOUNT = 1_000_000;
	private static final String DISCOUNT_TYPE_NAME = "fixed amount";
	private final int amount;

	public DiscountByFixedAmount(int amount) {
		this.amount = amount;
		validate(amount);
	}

	private static void validate(Integer amount) throws IllegalArgumentException {
		if (amount <= 0 || amount >= MAX_AMOUNT) {
			throw new IllegalArgumentException("Amount must be between 0 and " + MAX_AMOUNT);
		}
	}

	@Override
	public int discount(int originalPrice) {
		return originalPrice - amount;
	}

	public String getType() {
		return DISCOUNT_TYPE_NAME;
	}

	@Override
	public int getAmount() {
		return amount;
	}
}
