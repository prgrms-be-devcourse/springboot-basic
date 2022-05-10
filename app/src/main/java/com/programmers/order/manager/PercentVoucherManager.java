package com.programmers.order.manager;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.domain.PercentVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;

public class PercentVoucherManager implements VoucherClientManager, VoucherInternalManager {
	@Override
	public Voucher create(long quantity, long discountValue, LocalDateTime expirationAt) {
		return PercentVoucher.builder()
				.voucherId(UUID.randomUUID())
				.voucherType(VoucherType.FIX)
				.discountValue(discountValue)
				.quantity(quantity)
				.expirationAt(expirationAt)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
	}

	@Override
	public Voucher create(UUID voucherId, long discountValue, long quantity,
			LocalDateTime expirationAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
		return PercentVoucher.builder()
				.voucherId(voucherId)
				.voucherType(VoucherType.FIX)
				.discountValue(discountValue)
				.quantity(quantity)
				.expirationAt(expirationAt)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.build();
	}


	@Override
	public VoucherType getType() {
		return VoucherType.PERCENT;
	}
}
