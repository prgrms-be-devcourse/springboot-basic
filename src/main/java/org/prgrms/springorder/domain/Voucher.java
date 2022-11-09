package org.prgrms.springorder.domain;

import java.util.UUID;

public abstract class Voucher {

	private final UUID voucherId;

	private final long value;

	public abstract long discount(long beforeDiscount);

	public Voucher(UUID voucherId, long value) {
		this.voucherId = voucherId;
		this.value = value;
	}

	public UUID getVoucherId() {
		return voucherId;
	}

	protected long getValue() {
		return value;
	}

}
