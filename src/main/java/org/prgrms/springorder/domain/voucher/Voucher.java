package org.prgrms.springorder.domain.voucher;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Voucher {

	private final UUID voucherId;
	private final double value;
	private final LocalDateTime createdAt;
	private final VoucherType voucherType;

	public abstract int discount(double beforeDiscount);

	public abstract void validateValue(double value);

	public Voucher(UUID voucherId, double value, LocalDateTime createdAt,
		VoucherType voucherType) {
		validateValue(value);
		this.voucherId = voucherId;
		this.value = value;
		this.createdAt = createdAt;
		this.voucherType = voucherType;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public double getValue() {
		return value;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	// @Override
	// public String toString() {
	// 	return String.format("%s,%s,%d", getVoucherId(),voucherType.getName(),(int)getValue());
	// }
@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Voucher voucher = (Voucher)o;
		return Double.compare(voucher.getValue(), getValue()) == 0 && Objects.equals(getVoucherId(),
			voucher.getVoucherId()) && Objects.equals(getCreatedAt(), voucher.getCreatedAt())
			&& getVoucherType() == voucher.getVoucherType();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getVoucherId(), getValue(), getCreatedAt(), getVoucherType());
	}
}
