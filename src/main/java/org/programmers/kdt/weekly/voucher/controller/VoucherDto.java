package org.programmers.kdt.weekly.voucher.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.programmers.kdt.weekly.voucher.annotation.DateCheck;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;

public class VoucherDto {
	public record UpdateRequest(
		@NotNull
		int value) {
	}

	public record CreateRequest(
		@NotNull
		VoucherType voucherType,

		@NotNull
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

	public record DateRequest(

		@NotNull
		@DateCheck(message = "시작일은 현재 날짜 이후일 수 없습니다.")
		LocalDate begin,

		@NotNull
		@DateCheck(message = "종료일은 현재 날짜 이후일 수 없습니다.")
		LocalDate end) {

	}
}
