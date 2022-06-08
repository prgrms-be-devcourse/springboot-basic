package org.programmers.kdt.weekly.voucher.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;

public class VoucherDto {
	public record UpdateRequest(
		@NotNull @Size
		int value) {
	}

	public record CreateRequest(
		@NotNull
		VoucherType voucherType,

		@NotNull @Size
		int value) {
	}

	public record Response(
		UUID id,
		VoucherType type,
		int value,
		LocalDateTime createdAt) {

		public static Response from(Voucher voucher) {
			return new Response(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getValue(),
				voucher.getCreatedAt());
		}

		public static Voucher to(Response response) {
			return response.type()
				.create(response.id(), response.value(), response.createdAt());
		}
	}
}
