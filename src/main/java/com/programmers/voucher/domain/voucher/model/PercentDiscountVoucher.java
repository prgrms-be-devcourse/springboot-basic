package com.programmers.voucher.domain.voucher.model;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public class PercentDiscountVoucher implements Voucher {

	private static final Logger log = LoggerFactory.getLogger(PercentDiscountVoucher.class);
	private static final int PERCENT_100 = 100;
	private final UUID voucherId;
	private final double percent;

	public PercentDiscountVoucher(UUID voucherId, String percent) {
		this.voucherId = voucherId;
		this.percent = validateDiscount(percent);
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public double discount(double beforeDiscount) {
		return beforeDiscount * (percent / PERCENT_100);
	}

	@Override
	public String toString() {
		return "ID: " + voucherId + ", Type: PercentDiscountVoucher, Discount: " + percent + "%";
	}

	private double validateDiscount(String discount) {
		double parsedDiscount;
		try {
			parsedDiscount = Double.parseDouble(discount);
		} catch (IllegalArgumentException e) {
			log.error(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
			throw new IllegalArgumentException(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
		}

		if (parsedDiscount <= 0 || parsedDiscount > 100) {
			log.error(ExceptionMessage.OUT_OF_DISCOUNT_PERCENT_RANGE.getMessage());
			throw new IllegalArgumentException(ExceptionMessage.OUT_OF_DISCOUNT_PERCENT_RANGE.getMessage());
		}
		return parsedDiscount;
	}
}
