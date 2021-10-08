package com.prgrms.w3springboot.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {
	private static final VoucherFactory instance = new VoucherFactory();

	private VoucherFactory() {
	}

	public static VoucherFactory getInstance() {
		return instance;
	}

	public Voucher createVoucher(UUID voucherId, long discountAmount, VoucherType voucherType,
		LocalDateTime localDateTime) {
		switch (voucherType) {
			case FIXED:
				return new FixedAmountVoucher(voucherId, discountAmount, voucherType, localDateTime);
			case PERCENT:
				return new PercentAmountVoucher(voucherId, discountAmount, voucherType, localDateTime);
			default:
				throw new IllegalArgumentException("잘못된 타입을 입력받았습니다.");
		}
	}
}
