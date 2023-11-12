package com.programmers.springbasic.repository.dto.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;

public record VoucherResponse(UUID voucherId, VoucherType voucherType, long discountValue, LocalDateTime createdAt) {

	public static VoucherResponse from(Voucher voucher) {
		return new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountValue(), voucher.getCreatedAt());
	}
}
