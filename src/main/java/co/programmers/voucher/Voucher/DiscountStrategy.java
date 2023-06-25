package co.programmers.voucher.Voucher;

public interface DiscountStrategy {
	int discount(int originalPrice, int amount);
}
