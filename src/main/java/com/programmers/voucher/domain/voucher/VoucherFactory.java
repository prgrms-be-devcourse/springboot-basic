package com.programmers.voucher.domain.voucher;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.voucher.exception.WrongVoucherTypeException;

@Component
public class VoucherFactory {

	public Voucher makeVoucher(String voucherType, UUID voucherId, int discountAmount) {

		switch (voucherType) {
			case "FixedDiscountVoucher":
				return new FixedDiscountVoucher(voucherId, discountAmount);
			case "PercentDiscountVoucher":
				return new PercentDiscountVoucher(voucherId, discountAmount);
			default:
				throw new WrongVoucherTypeException();
		}
	}
}
