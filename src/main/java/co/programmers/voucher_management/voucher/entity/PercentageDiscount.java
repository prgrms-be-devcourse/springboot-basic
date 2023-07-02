package co.programmers.voucher_management.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PercentageDiscount implements DiscountStrategy {
	private static final int MAX_AMOUNT = 100;
	private static final String DISCOUNT_TYPE_NAME = "percent";
	private static final Logger logger = LoggerFactory.getLogger(PercentageDiscount.class);
	private final int amount;

	public PercentageDiscount(int amount) {
		validate(amount);
		this.amount = amount;
	}

	private static void validate(Integer amount) throws IllegalArgumentException {
		if (amount <= 0 || amount >= MAX_AMOUNT) {
			logger.debug("User Input - amount : {}, Amount must be between 0 and {}", amount, MAX_AMOUNT);
			throw new IllegalArgumentException("Amount must be between 0 and 100");
		}
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public int discount(int originalPrice) {
		return originalPrice * (1 - amount / 100);
	}

	public String getType() {
		return DISCOUNT_TYPE_NAME;
	}
}
