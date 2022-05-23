package org.programmers.kdt.weekly.voucher.controller.restController;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.programmers.kdt.weekly.voucher.model.VoucherType;

public class VoucherDto {
	public record VoucherUpdateRequest(
		@NotNull
		int value) {
	}

	public record VoucherCreateRequest(
		@NotNull
		VoucherType voucherType,

		@NotNull
		int value) {
	}

	public record VoucherResponse(
		UUID id,
		VoucherType type,
		int value,
		LocalDateTime createdAt) {
	}

}
