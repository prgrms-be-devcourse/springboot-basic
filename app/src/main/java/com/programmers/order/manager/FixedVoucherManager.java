package com.programmers.order.manager;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.domain.FixedVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;

public class FixedVoucherManager implements VoucherClientManager, VoucherInternalManager {
	@Override
	public Voucher create(long quantity, long discountValue, LocalDateTime expirationAt) {

		return FixedVoucher.builder()
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

		return FixedVoucher.builder()
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
		return VoucherType.FIX;
	}
}
