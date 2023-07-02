package co.programmers.voucher_management.voucher.entity;

public interface DiscountStrategy {

	int discount(int originalPrice);

	String getType();

	int getAmount();
}
