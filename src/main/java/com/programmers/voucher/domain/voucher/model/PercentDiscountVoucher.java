package com.programmers.voucher.domain.voucher.model;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public class PercentDiscountVoucher extends Voucher {

	private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucher.class);
	private static final double MIN_PERCENT = 0;
	private static final double MAX_PERCENT = 100;

	public PercentDiscountVoucher(UUID voucherId, VoucherType voucherType, String discount) {
		super(voucherId, voucherType, discount);
	}

	@Override
	protected double validateDiscount(String discount) {
		double parsedDiscount;
		try {
			parsedDiscount = Double.parseDouble(discount);
		} catch (NumberFormatException e) {
			log.error(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
			throw new NumberFormatException(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
		}

		if (parsedDiscount <= MIN_PERCENT || parsedDiscount > MAX_PERCENT) {
			log.error(ExceptionMessage.OUT_OF_DISCOUNT_PERCENT_RANGE.getMessage());
			throw new IllegalArgumentException(ExceptionMessage.OUT_OF_DISCOUNT_PERCENT_RANGE.getMessage());
		}
		return parsedDiscount;
	}

	@Override
	public String toString() {
		return "ID: " + voucherId + ", Type: " + voucherType.getName() + ", Discount: " + discount + "%";
	}
}
