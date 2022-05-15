package com.programmers.order.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.programmers.order.domain.VoucherType;

import lombok.Builder;

public class VoucherDto {

	@Builder
	public record Create(
			@NotNull(message = "쿠폰 형식을 입력해주세요.")
			VoucherType voucherType,

			@Range(min = 1, max = 100_000_000, message = "voucherDto.discountValue.range")
			long discountValue,

			@Range(min = 1, max = 1_000_000, message = "voucherDto.quantity.range")
			long quantity,

			@Future(message = "과거 시간을 입력했습니다. 다시 입력해 주세요.")
			@NotNull(message = "값을 입력해주세요.")
			@DateTimeFormat(pattern = "yyyy-MM-dd")
			LocalDateTime expirationAt
	) {

	}

	@Builder
	public record Update(
			@NotNull
			UUID voucherId,

			@NotNull(message = "쿠폰 형식을 입력해주세요.")
			VoucherType voucherType,

			@Range(min = 1, max = 100_000_000, message = "voucherDto.discountValue.range")
			long discountValue,

			@Range(min = 1, max = 1_000_000, message = "voucherDto.quantity.range")
			long quantity,

			@Future(message = "과거 시간을 입력했습니다. 다시 입력해 주세요.")
			@NotNull(message = "값을 입력해주세요.")
			@DateTimeFormat(pattern = "yyyy-MM-dd")
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
