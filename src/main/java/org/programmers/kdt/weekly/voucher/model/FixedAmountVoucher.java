package org.programmers.kdt.weekly.voucher.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;
import org.programmers.kdt.weekly.voucher.exception.InvalidVoucherValueException;

public class FixedAmountVoucher implements Voucher {

	private final UUID voucherId;
	private int value;
	private final LocalDateTime createdAt;
	private final VoucherType voucherType = VoucherType.FIXED;

	public FixedAmountVoucher(UUID voucherId, int value) {
		validation(value);
		this.voucherId = voucherId;
		this.value = value;
		this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
	}

	public FixedAmountVoucher(UUID voucherId, int value, LocalDateTime createdAt) {
		validation(value);
		this.voucherId = voucherId;
		this.value = value;
		this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
	}

	private void validation(int value) {
		if (value <= 0) {
			throw new InvalidVoucherValueException("할인 금액은 0 보다 커야합니다. ", ErrorCode.INVALID_REQUEST_VALUE);
		}
	}

	@Override
	public int discount(int beforeDiscount) {

		if (beforeDiscount < value) {
			return 0;
		}

		return beforeDiscount - value;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void changeValue(int value) {
		this.value = value;
	}

	@Override
	public VoucherType getVoucherType() {
		return voucherType;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", voucherId)
			.append("type", voucherType)
			.append("value", value + voucherType.getMeasure())
			.append("createdAt", createdAt)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof FixedAmountVoucher)) {
			return false;
		}
		FixedAmountVoucher that = (FixedAmountVoucher)o;
		return Objects.equals(voucherId, that.voucherId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voucherId);
	}
}