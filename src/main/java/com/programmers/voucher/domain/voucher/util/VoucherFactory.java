package com.programmers.voucher.domain.voucher.util;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.voucher.domain.voucher.model.FixedDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;

public class VoucherFactory {

	public static Voucher createVoucher(VoucherType voucherType, String discount) {
		if (voucherType.equals(VoucherType.FIXED)) {
			return new FixedDiscountVoucher(UUID.randomUUID(), discount, voucherType, LocalDateTime.now());
		}
		return new PercentDiscountVoucher(UUID.randomUUID(), discount, voucherType, LocalDateTime.now());
	}

	public static Voucher createVoucher(UUID voucherId, VoucherType voucherType, String discount,
		LocalDateTime createdAt) {
		if (voucherType.equals(VoucherType.FIXED)) {
			return new FixedDiscountVoucher(voucherId, discount, voucherType, createdAt);
		}
		return new PercentDiscountVoucher(voucherId, discount, voucherType, createdAt);
	}
}
