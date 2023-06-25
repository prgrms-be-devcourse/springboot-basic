package co.programmers.voucher.Voucher;

public class DiscountByFixedAmount implements DiscountStrategy {
	private static final DiscountByFixedAmount INSTANCE = new DiscountByFixedAmount();

	private DiscountByFixedAmount() {
	}

	public static DiscountByFixedAmount getInstance() {
		return INSTANCE;
	}

	@Override
	public int discount(int originalPrice, int amount) {
		return originalPrice - amount;
	}
}
