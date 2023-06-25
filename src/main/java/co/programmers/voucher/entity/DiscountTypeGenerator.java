package co.programmers.voucher.entity;

public class DiscountTypeGenerator {
	public static DiscountStrategy of(String type, Integer amount) throws IllegalArgumentException {
		DiscountStrategy discountStrategy;
		switch (type.toLowerCase()) {
			case ("fixed"):
				discountStrategy = DiscountByFixedAmount.getInstance(amount);
				break;
			case ("percent"):
				discountStrategy = DiscountByPercentage.getInstance(amount);
				break;
			default:
				throw new IllegalArgumentException("Invalid type of discount");
		}
		return discountStrategy;
	}
}
