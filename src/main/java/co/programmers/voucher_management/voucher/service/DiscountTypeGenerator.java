package co.programmers.voucher_management.voucher.service;

import static co.programmers.voucher_management.exception.ErrorCode.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.programmers.voucher_management.exception.InvalidDataException;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.FixedDiscount;
import co.programmers.voucher_management.voucher.entity.PercentageDiscount;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class DiscountTypeGenerator {
	private static final Logger logger = LoggerFactory.getLogger(DiscountTypeGenerator.class);
	static DiscountStrategy discountStrategy;

	public static DiscountStrategy of(String type, Integer amount) {
		switch (type.toLowerCase()) {
			case ("fixed") -> discountStrategy = new FixedDiscount(amount);
			case ("percent") -> discountStrategy = new PercentageDiscount(amount);
			default -> {
				logger.debug("User Input - Discount Type : {}", type);
				throw new InvalidDataException(INVALID_DISCOUNT_TYPE);
			}
		}
		return discountStrategy;
	}
}
