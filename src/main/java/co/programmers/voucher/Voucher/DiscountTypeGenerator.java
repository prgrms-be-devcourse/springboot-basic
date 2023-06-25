package co.programmers.voucher.Voucher;

public class DiscountTypeGenerator {
	public static DiscountStrategy of(String type){
		DiscountStrategy discountStrategy;
		switch (type.toLowerCase()) {
			case ("fixed"):
				discountStrategy = DiscountByFixedAmount.getInstance();
				break;
			case ("percent"):
				discountStrategy = DiscountByPercentage.getInstance();
				break;
			default:
				throw new IllegalArgumentException("Invalid type of discount");
		}
		return discountStrategy;
	}
}
