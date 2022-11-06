package com.programmers.voucher.domain.voucher.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.exception.ExceptionMessage;
import com.programmers.voucher.exception.OutOfPercentRangeException;
import com.programmers.voucher.exception.WrongDiscountTypeException;
import com.programmers.voucher.exception.WrongVoucherTypeException;

@Component
public class Validator {

	private final Logger log = LoggerFactory.getLogger(Validator.class);

	public void validateVoucherType(String type) {
		if (!(type.equals(VoucherType.FIXED.getType()) || type.equals(VoucherType.PERCENT.getType()))) {
			log.error(ExceptionMessage.WRONG_VOUCHER_TYPE.getMessage());
			throw new WrongVoucherTypeException();
		}
	}

	public void validateDiscount(String type, String discount) {
		validateDiscountIsNumber(discount);
		validateDiscountPercentRange(type, discount);
	}

	private void validateDiscountIsNumber(String discount) {
		discount = discount.replaceAll(" ", "");

		if (discount.equals("") || !discount.chars().allMatch(Character::isDigit)) {
			log.error(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
			throw new WrongDiscountTypeException();
		}
	}

	private void validateDiscountPercentRange(String type, String discount) {
		if (type.equals(VoucherType.PERCENT.getType())) {
			int parsedDiscount = Integer.parseInt(discount);

			if (parsedDiscount <= 0 || parsedDiscount > 100) {
				log.error(ExceptionMessage.OUT_OF_PERCENT_RANGE.getMessage());
				throw new OutOfPercentRangeException();
			}
		}
	}
}
