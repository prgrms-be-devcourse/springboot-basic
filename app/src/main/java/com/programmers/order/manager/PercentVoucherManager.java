package com.programmers.order.manager;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.order.controller.dto.VoucherDto;
import com.programmers.order.domain.FixedVoucher;
import com.programmers.order.domain.PercentVoucher;
import com.programmers.order.domain.Voucher;
import com.programmers.order.domain.VoucherType;

@Component
public class PercentVoucherManager implements VoucherClientManager, VoucherInternalManager {

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
	public Voucher create(VoucherDto.Create createDto) {
		return PercentVoucher.builder()
				.voucherId(UUID.randomUUID())
				.voucherType(createDto.voucherType())
				.discountValue(createDto.discountValue())
				.quantity(createDto.quantity())
				.expirationAt(createDto.expirationAt())
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
	}

	@Override
	public VoucherType getType() {
		return VoucherType.PERCENT;
	}

	@Override
	public Voucher update(VoucherDto.Update updateDto) {
		return PercentVoucher
				.builder()
				.voucherId(updateDto.voucherId())
				.voucherType(updateDto.voucherType())
				.discountValue(updateDto.discountValue())
				.quantity(updateDto.quantity())
				.expirationAt(updateDto.expirationAt())
				.updatedAt(LocalDateTime.now())
				.build();
	}
}
