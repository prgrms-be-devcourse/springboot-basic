package com.programmers.springbasic.controller.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.Voucher;
import com.programmers.springbasic.entity.VoucherType;

public record ListVouchersResponse(
	UUID voucherId,
	VoucherType voucherType,
	long amount,
	long percent) {
	// public static ListVouchersResponse from(Voucher voucher) {
	//
	// }
}
