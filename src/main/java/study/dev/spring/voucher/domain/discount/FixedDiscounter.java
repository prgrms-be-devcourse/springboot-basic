package study.dev.spring.voucher.domain.discount;

public class FixedDiscounter implements Discounter {

	@Override
	public double discount(
		final double price,
		final double discountAmount
	) {
		return price - discountAmount;
	}
}
