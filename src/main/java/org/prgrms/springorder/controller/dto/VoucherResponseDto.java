package org.prgrms.springorder.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgrms.springorder.domain.voucher.VoucherType;

public class VoucherResponseDto {

	private UUID voucherId;

	private double value;

	private LocalDateTime voucherCreatedAt;

	private LocalDateTime updatedAt;

	private VoucherType voucherType;

	public VoucherResponseDto(UUID voucherId, double value, LocalDateTime voucherCreatedAt,
		LocalDateTime updatedAt, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.value = value;
		this.voucherCreatedAt = voucherCreatedAt;
		this.updatedAt = updatedAt;
		this.voucherType = voucherType;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public double getValue() {
		return value;
	}

	public LocalDateTime getVoucherCreatedAt() {
		return voucherCreatedAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%d", getVoucherId(), voucherType.getName(), (int)getValue());
	}
}
