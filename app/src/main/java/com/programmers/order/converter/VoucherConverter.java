package com.programmers.order.converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.programmers.order.controller.dto.PageDto;
import com.programmers.order.controller.dto.VoucherDto;
import com.programmers.order.domain.Voucher;
import com.programmers.order.provider.VoucherProvider;

import ch.qos.logback.classic.util.LevelToSyslogSeverity;

@Component
public class VoucherConverter {

	private final VoucherProvider voucherProvider;

	public VoucherConverter(VoucherProvider voucherProvider) {
		this.voucherProvider = voucherProvider;
	}

	public Converter<VoucherDto.Create, Voucher> createDtoToDomain() {
		return createDto -> voucherProvider.clientOf(createDto.voucherType())
				.create(createDto);
	}

	public Converter<Voucher, VoucherDto.Response> domainToResponseDto() {
		return voucher -> VoucherDto.Response
				.builder()
				.voucherId(voucher.getVoucherId())
				.voucherType(voucher.getVoucherType())
				.discountValue(voucher.getDiscountValue())
				.quantity(voucher.getQuantity())
				.expirationAt(voucher.getExpirationAt())
				.createdAt(voucher.getCreatedAt())
				.updatedAt(voucher.getUpdatedAt())
				.build();
	}

	public Converter<VoucherDto.Update, Voucher> updateDtoToDomain() {
		return updateDto -> voucherProvider.clientOf(updateDto.voucherType())
				.update(updateDto);
	}

	public Converter<Page<Voucher>, PageDto.Response<VoucherDto.Response, Voucher>> domainToResponseDtos() {
		return pagingVoucher -> {
			Function<Voucher, VoucherDto.Response> toResponse = domain -> VoucherDto.Response.builder()
					.voucherId(domain.getVoucherId())
					.voucherType(domain.getVoucherType())
					.discountValue(domain.getDiscountValue())
					.quantity(domain.getQuantity())
					.expirationAt(domain.getExpirationAt())
					.createdAt(domain.getCreatedAt())
					.updatedAt(domain.getUpdatedAt())
					.build();

			return new PageDto.Response<>(pagingVoucher, toResponse);
		};
	}
}
