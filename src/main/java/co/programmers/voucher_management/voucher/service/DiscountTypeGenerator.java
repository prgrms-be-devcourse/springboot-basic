package co.programmers.voucher_management.voucher.service;

import org.springframework.stereotype.Service;

import co.programmers.voucher_management.exception.InvalidUserInputException;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.FixedDiscount;
import co.programmers.voucher_management.voucher.entity.PercentageDiscount;

@Service
public class DiscountTypeGenerator {
	static DiscountStrategy discountStrategy;

	public static DiscountStrategy of(String type, Integer amount) {
		switch (type.toLowerCase()) {
			case ("fixed"):
				discountStrategy = new FixedDiscount(amount);
				break;
			case ("percent"):
				discountStrategy = new PercentageDiscount(amount);
				break;
			default:
				throw new InvalidUserInputException("Invalid type of discount");
		}
		return discountStrategy;
	}
}
