package com.programmers.voucher.domain.voucher.model;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public class FixedDiscountVoucher implements Voucher {

	private static final Logger log = LoggerFactory.getLogger(FixedDiscountVoucher.class);
	private final UUID voucherId;
	private final double discountAmount;

	public FixedDiscountVoucher(UUID voucherId, String discountAmount) {
		this.voucherId = voucherId;
		this.discountAmount = validateDiscount(discountAmount);
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public double discount(double beforeDiscount) {
		return beforeDiscount - discountAmount;
	}

	@Override
	public String toString() {
		return "ID: " + voucherId + ", Type: FixedDiscountVoucher, Discount: " + discountAmount;
	}

	private double validateDiscount(String discount) {
		double parsedDiscount;
		try {
			parsedDiscount = Double.parseDouble(discount);
		} catch (IllegalArgumentException e) {
			log.error(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
			throw new IllegalArgumentException(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
		}

		if (parsedDiscount < 0) {
			log.error(ExceptionMessage.OUT_OF_DISCOUNT_AMOUNT_RANGE.getMessage());
			throw new IllegalArgumentException(ExceptionMessage.OUT_OF_DISCOUNT_AMOUNT_RANGE.getMessage());
		}
		return parsedDiscount;
	}
}
