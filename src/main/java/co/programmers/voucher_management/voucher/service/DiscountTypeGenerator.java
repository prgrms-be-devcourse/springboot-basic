package co.programmers.voucher_management.voucher.service;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.voucher.entity.DiscountByFixedAmount;
import co.programmers.voucher_management.voucher.entity.DiscountByPercentage;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;

@Service
public class DiscountTypeGenerator {
	static DiscountStrategy discountStrategy;

	public static DiscountStrategy of(String type, Integer amount) throws IllegalArgumentException {
		switch (type.toLowerCase()) {
			case ("fixed"):
				discountStrategy = new DiscountByFixedAmount(amount);
				break;
			case ("percent"):
				discountStrategy = new DiscountByPercentage(amount);
				break;
			default:
				throw new IllegalArgumentException("Invalid type of discount");
		}
		return discountStrategy;
	}
}
