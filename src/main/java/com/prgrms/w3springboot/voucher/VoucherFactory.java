package com.prgrms.w3springboot.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {
	public Voucher createVoucher(UUID voucherId, long discountAmount, VoucherType voucherType) {
		switch (voucherType) {
			case FIXED:
				return new FixedAmountVoucher(voucherId, discountAmount, voucherType, LocalDateTime.now());
			case PERCENT:
				return new PercentAmountVoucher(voucherId, discountAmount, voucherType, LocalDateTime.now());
			default:
				throw new IllegalArgumentException("잘못된 타입을 입력받았습니다.");
		}
	}
}
