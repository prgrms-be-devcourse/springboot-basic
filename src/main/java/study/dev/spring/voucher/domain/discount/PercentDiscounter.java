package study.dev.spring.voucher.domain.discount;

public class PercentDiscounter implements Discounter {

	@Override
	public double discount(
		final double price,
		final double discountAmount
	) {
		return price - (price * (discountAmount / 100));
	}
}
