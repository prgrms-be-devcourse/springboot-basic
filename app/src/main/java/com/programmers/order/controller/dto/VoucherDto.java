package com.programmers.order.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.domain.VoucherType;

import lombok.Builder;

public class VoucherDto {

	@Builder
	public record Create(
			VoucherType voucherType,
			long discountValue,
			long quantity,
			LocalDateTime expirationAt
	) {

	}

	@Builder
	public record Response(
			UUID voucherId,
			VoucherType voucherType,
			long discountValue,
			long quantity,
			LocalDateTime expirationAt,
			LocalDateTime createdAt,
			LocalDateTime updatedAt
	) {

	}
}
