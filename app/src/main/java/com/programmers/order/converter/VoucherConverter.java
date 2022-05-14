package com.programmers.order.converter;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
}
