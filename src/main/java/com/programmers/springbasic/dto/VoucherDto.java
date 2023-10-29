package com.programmers.springbasic.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;

public record VoucherDto(UUID voucherId, VoucherType voucherType, long discountValue) {

	public static VoucherDto from(Voucher voucher) {
		return new VoucherDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountValue());
	}
}
