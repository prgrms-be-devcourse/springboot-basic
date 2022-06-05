package com.voucher.vouchermanagement.domain.voucher.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;

public class UpdateVoucherRequest {
	private UUID id;
	private long value;
	private VoucherType type;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime createdAt;

	protected UpdateVoucherRequest() {
	}

	public UpdateVoucherRequest(UUID id, long value, String type, LocalDateTime createdAt) {
		this.id = id;
		this.value = value;
		this.type = VoucherType.fromName(type);
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public long getValue() {
		return value;
	}

	public VoucherType getType() {
		return type;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
