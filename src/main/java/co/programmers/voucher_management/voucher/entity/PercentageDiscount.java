package co.programmers.voucher_management.voucher.entity;

import static co.programmers.voucher_management.exception.ErrorCode.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.programmers.voucher_management.exception.InvalidDataException;

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
		if (amount <= 0 || amount > MAX_AMOUNT) {
			logger.debug("User Input - amount : {}, Amount must be between 0 and {}", amount, MAX_AMOUNT);
			throw new InvalidDataException(INVALID_PERCENT_AMOUNT);

		}
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public int discount(int originalPrice) {
		return (int)(originalPrice * (1 - (double)amount / 100));
	}

	public String getType() {
		return DISCOUNT_TYPE_NAME;
	}
}
