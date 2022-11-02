package com.programmers.voucher.domain;

import java.util.UUID;

import com.programmers.voucher.exception.WrongVoucherTypeException;

public class VoucherFactory {

	public Voucher makeVoucher(String voucherType, UUID voucherId, int discountAmount) {

		switch (voucherType) {
			case "fixed":
				return new FixedDiscountVoucher(voucherId, discountAmount);
			case "percent":
				return new PercentDiscountVoucher(voucherId, discountAmount);
			default:
				throw new WrongVoucherTypeException();
		}
	}
}
