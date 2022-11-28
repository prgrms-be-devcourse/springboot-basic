package org.prgrms.springorder.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgrms.springorder.domain.voucher.VoucherType;

public class VoucherResponseDto {

	private UUID voucherId;

	private double value;

	private LocalDateTime voucherCreatedAt;

	private VoucherType voucherType;

	public VoucherResponseDto() {
	}

	public VoucherResponseDto(UUID voucherId, double value, LocalDateTime voucherCreatedAt,
		VoucherType voucherType) {
		this.voucherId = voucherId;
		this.value = value;
		this.voucherCreatedAt = voucherCreatedAt;
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

	public VoucherType getVoucherType() {
		return voucherType;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%d", getVoucherId(), voucherType.getName(), (int)getValue());
	}
}
