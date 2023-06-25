package co.programmers.voucher.Voucher;

public class DiscountByPercentage implements DiscountStrategy {
	private static final DiscountByPercentage INSTANCE = new DiscountByPercentage();

	private DiscountByPercentage() {
	}

	public static DiscountByPercentage getInstance() {
		return INSTANCE;
	}

	@Override
	public int discount(int originalPrice, int amount) {
		return originalPrice * (1 - amount / 100);
	}
}
