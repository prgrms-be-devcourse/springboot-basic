package org.programmers.kdt.weekly.voucher.controller.restController;

import java.time.LocalDateTime;
import java.util.UUID;

import org.programmers.kdt.weekly.voucher.model.VoucherType;

public record VoucherDto(UUID id, VoucherType type, int value, LocalDateTime createdAt) {

	public VoucherDto(UUID id, VoucherType type, int value, LocalDateTime createdAt) {
		this.id = id;
		this.type = type;
		this.value = value;
		this.createdAt = createdAt;
	}
}
