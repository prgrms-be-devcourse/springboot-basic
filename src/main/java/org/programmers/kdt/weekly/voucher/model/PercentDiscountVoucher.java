package org.programmers.kdt.weekly.voucher.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PercentDiscountVoucher implements Voucher {

	private final UUID voucherId;
	private int value;
	private final LocalDateTime createdAt;
	private final VoucherType voucherType = VoucherType.PERCENT;

	public PercentDiscountVoucher(UUID voucherId, int value) {
		validation(value);

		this.voucherId = voucherId;
		this.value = value;
		this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
	}

	public PercentDiscountVoucher(UUID voucherId, int value, LocalDateTime createdAt) {
		validation(value);

		this.voucherId = voucherId;
		this.value = value;
		this.createdAt = createdAt.truncatedTo(ChronoUnit.MICROS);
	}

	private void validation(int value) {
		if (value <= 0 || value > 100) {
			throw new IllegalArgumentException("잘못된 입력입니다.");
		}
	}

	@Override
	public int discount(int beforeDiscount) {
		return beforeDiscount * (100 - value) / 100;
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
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public VoucherType getVoucherType() {
		return voucherType;
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
		if (!(o instanceof PercentDiscountVoucher)) {
			return false;
		}
		PercentDiscountVoucher that = (PercentDiscountVoucher)o;
		return Objects.equals(getVoucherId(), that.getVoucherId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getVoucherId());
	}
}