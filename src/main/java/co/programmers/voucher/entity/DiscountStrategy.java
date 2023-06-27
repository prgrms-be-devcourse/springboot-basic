package co.programmers.voucher.entity;

public interface DiscountStrategy {

	int discount(int originalPrice);

	String getType();

	int getAmount();
}
