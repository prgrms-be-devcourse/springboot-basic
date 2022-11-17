package org.prgrms.springorder.domain.voucher;

import java.util.UUID;

public abstract class Voucher {

	private final UUID voucherId;

	private final double value;

	public abstract int discount(double beforeDiscount);

	public abstract void validateValue(double value);

	public Voucher(UUID voucherId, double value) {
		validateValue(value);
		this.voucherId = voucherId;
		this.value = value;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	public double getValue() {
		return value;
	}

}
