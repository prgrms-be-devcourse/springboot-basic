package com.programmers.order.dto;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.programmers.order.domain.Voucher;

@JsonPropertyOrder({"voucherId", "voucherType", "discountValue", "createdAt"})
public class VoucherForCsv implements Voucher {

	@JsonProperty(index = 0)
	private final UUID voucherId;

	@JsonProperty(index = 1)
	private final String voucherType;

	@JsonProperty(index = 2)
	private final String discountValue;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty(index = 3)
	private final LocalDateTime createdAt;

	@ConstructorProperties({"voucherId", "voucherType", "discountValue", "createdAt"})
	public VoucherForCsv(UUID voucherId, String voucherType, String discountValue, LocalDateTime createdAt) {
		this.voucherId = voucherId;
		this.voucherType = voucherType;
		this.discountValue = discountValue;
		this.createdAt = createdAt;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String show() {
		return "voucherId=" + voucherId +
				", voucherType='" + voucherType + '\'' +
				", discountValue='" + discountValue + '\'' +
				", createdAt=" + createdAt;
	}

	@Override
	public String toString() {
		return "VoucherForCsv{" +
				"voucherId=" + voucherId +
				", voucherType='" + voucherType + '\'' +
				", discountValue='" + discountValue + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}