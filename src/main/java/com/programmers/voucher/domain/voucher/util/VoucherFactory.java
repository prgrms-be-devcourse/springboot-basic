package com.programmers.voucher.domain.voucher.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.voucher.domain.voucher.model.FixedDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;

@Component
public class VoucherFactory {

	public Voucher makeVoucher(String voucherType, UUID voucherId, int discountAmount) {
		if (voucherType.equals(VoucherType.FIXED.getType())) {
			return new FixedDiscountVoucher(voucherId, discountAmount);
		}

		return new PercentDiscountVoucher(voucherId, discountAmount);
	}
}
