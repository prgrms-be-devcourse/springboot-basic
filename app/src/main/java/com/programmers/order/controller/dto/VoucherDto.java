package com.programmers.order.controller.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.programmers.order.domain.VoucherType;

import lombok.Builder;
import lombok.Data;

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

	@Data
	public static class Search {
		VoucherType type;

		@PastOrPresent(message = "미래 시간을 입력했습니다. 다시 입력해주세요. ")
		LocalDateTime created;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		LocalDateTime createdEnd;

		@Builder
		public Search(VoucherType type, LocalDateTime created, LocalDateTime createdEnd) {
			this.type = type;
			this.created = created;
			if (Objects.nonNull(created) && createdEnd.isBefore(created)) {
				throw new IllegalArgumentException("바우처 생성 시간보다 전을 입력했습니다.");
			}
			this.createdEnd = createdEnd;
		}

		public String buildCondition() {
			StringBuilder conditions = new StringBuilder();

			if (Objects.nonNull(type)) {
				conditions.append("voucher_type = '" + type.name() + "' ");
			}

			if (Objects.nonNull(created) && Objects.nonNull(createdEnd)) {
				if (!conditions.isEmpty()) {
					conditions.append(" and ");
				}

				conditions.append(String.format("created_at between '%s' and '%s'", created, createdEnd));
			}

			if (!conditions.isEmpty()) {
				conditions.insert(0, " where ");
			}

			return conditions.toString();
		}
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
