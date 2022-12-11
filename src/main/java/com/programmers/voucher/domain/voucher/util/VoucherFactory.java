package com.programmers.voucher.domain.voucher.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.voucher.domain.voucher.model.FixedDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;

@Component
public class VoucherFactory {

	public Voucher createVoucher(VoucherType voucherType, String discount) {
		if (voucherType.equals(VoucherType.FIXED)) {
			return new FixedDiscountVoucher(UUID.randomUUID(), voucherType, discount);
		}
		return new PercentDiscountVoucher(UUID.randomUUID(), voucherType, discount);
	}

	public Voucher createVoucher(UUID voucherId, VoucherType voucherType, String discount) {
		if (voucherType.equals(VoucherType.FIXED)) {
			return new FixedDiscountVoucher(voucherId, voucherType, discount);
		}
		return new PercentDiscountVoucher(voucherId, voucherType, discount);
	}
}
