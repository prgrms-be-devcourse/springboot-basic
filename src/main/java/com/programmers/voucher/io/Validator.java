package com.programmers.voucher.io;

import org.springframework.stereotype.Component;

import com.programmers.voucher.domain.voucher.VoucherType;
import com.programmers.voucher.exception.OutOfPercentRangeException;
import com.programmers.voucher.exception.WrongDiscountTypeException;
import com.programmers.voucher.exception.WrongVoucherTypeException;

@Component
public class Validator {

	public void validateVoucherType(String type) {
		if (!(type.equals(VoucherType.FIXED.getType()) || type.equals(VoucherType.PERCENT.getType()))) {
			throw new WrongVoucherTypeException();
		}
	}

	public void validateDiscount(String type, String discount) {
		validateDiscountIsNumber(discount);
		validateDiscountPercentRange(type, discount);
	}

	private void validateDiscountIsNumber(String discount) {
		discount = discount.replaceAll(" ", "");

		if (discount.equals("")) {
			throw new WrongDiscountTypeException();
		}
		if (!discount.chars().allMatch(Character::isDigit)) {
			throw new WrongDiscountTypeException();
		}
	}

	private void validateDiscountPercentRange(String type, String discount) {
		if (type.equals(VoucherType.PERCENT.getType())) {
			int parsedDiscount = Integer.parseInt(discount);

			if (parsedDiscount <= 0 || parsedDiscount > 100) {
				throw new OutOfPercentRangeException();
			}
		}
	}
}
