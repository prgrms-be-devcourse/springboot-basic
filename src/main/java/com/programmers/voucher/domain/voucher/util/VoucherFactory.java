package com.programmers.voucher.domain.voucher.util;

import java.time.LocalDateTime;
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
			return new FixedDiscountVoucher(UUID.randomUUID(), discount, LocalDateTime.now(), voucherType,
				LocalDateTime.now());
		}
		return new PercentDiscountVoucher(UUID.randomUUID(), discount, LocalDateTime.now(), voucherType,
			LocalDateTime.now());
	}

	public Voucher createVoucher(UUID voucherId, VoucherType voucherType, String discount, LocalDateTime createdAt,
		LocalDateTime lastModifiedAt) {
		if (voucherType.equals(VoucherType.FIXED)) {
			return new FixedDiscountVoucher(voucherId, discount, createdAt, voucherType,
				lastModifiedAt);
		}
		return new PercentDiscountVoucher(voucherId, discount, createdAt, voucherType,
			lastModifiedAt);
	}
}
