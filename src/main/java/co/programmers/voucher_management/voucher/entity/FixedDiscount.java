package co.programmers.voucher_management.voucher.entity;

import static java.lang.Math.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.programmers.voucher_management.exception.InvalidUserInputException;

public class FixedDiscount implements DiscountStrategy {
	private static final int MAX_AMOUNT = 1_000_000;
	private static final String DISCOUNT_TYPE_NAME = "fixed";
	private static final Logger logger = LoggerFactory.getLogger(FixedDiscount.class);
	private final int amount;

	public FixedDiscount(int amount) {
		validate(amount);
		this.amount = amount;
	}

	private static void validate(Integer amount) {
		if (amount <= 0 || amount >= MAX_AMOUNT) {
			logger.debug("amount : {}, Amount must be between 0 and {}", amount, MAX_AMOUNT);
			throw new InvalidUserInputException("Amount must be between 0 and " + MAX_AMOUNT);
		}
	}

	@Override
	public int discount(int originalPrice) {
		return max(originalPrice - amount, 0);
	}

	public String getType() {
		return DISCOUNT_TYPE_NAME;
	}

	@Override
	public int getAmount() {
		return amount;
	}
}
