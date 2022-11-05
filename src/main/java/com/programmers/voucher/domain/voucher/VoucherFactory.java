package com.programmers.voucher.domain.voucher;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {

	public Voucher makeVoucher(String voucherType, UUID voucherId, int discountAmount) {
		if (voucherType.equals(VoucherType.FIXED.getType())) {
			return new FixedDiscountVoucher(voucherId, discountAmount);
		}

		return new PercentDiscountVoucher(voucherId, discountAmount);
	}
}
