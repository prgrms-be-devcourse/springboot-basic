package com.programmers.voucher.domain.voucher.model;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public class Voucher {

	private static final Logger log = LoggerFactory.getLogger(Voucher.class);
	private final UUID voucherId;
	private final double discount;
	private final VoucherType voucherType;

	public Voucher(UUID voucherId, VoucherType voucherType, String discount) {
		this.voucherId = voucherId;
		this.voucherType = voucherType;
		this.discount = validateDiscount(discount);
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public String toString() {
		if (voucherType.equals(VoucherType.FIXED)) {
			return "ID: " + voucherId + ", Type: " + voucherType.getName() + ", Discount: " + discount;
		}
		return "ID: " + voucherId + ", Type: " + voucherType.getName() + ", Discount: " + discount + "%";
	}

	private double validateDiscount(String discount) {
		double parsedDiscount;
		try {
			parsedDiscount = Double.parseDouble(discount);
		} catch (NumberFormatException e) {
			log.error(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
			throw new NumberFormatException(ExceptionMessage.WRONG_DISCOUNT_TYPE.getMessage());
		}

		if (voucherType.hasOutOfDiscountRange(parsedDiscount)) {
			if (voucherType.equals(VoucherType.FIXED)) {
				log.error(ExceptionMessage.OUT_OF_DISCOUNT_AMOUNT_RANGE.getMessage());
				throw new IllegalArgumentException(ExceptionMessage.OUT_OF_DISCOUNT_AMOUNT_RANGE.getMessage());
			}
			log.error(ExceptionMessage.OUT_OF_DISCOUNT_PERCENT_RANGE.getMessage());
			throw new IllegalArgumentException(ExceptionMessage.OUT_OF_DISCOUNT_PERCENT_RANGE.getMessage());
		}

		return parsedDiscount;
	}
}