package study.dev.spring.voucher.domain.discount;

@FunctionalInterface
public interface Discounter {

	double discount(double price, double discountAmount);
}
