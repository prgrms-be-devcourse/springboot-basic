package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
	private final UUID voucherId;
	private final long percent;
	private final LocalDateTime created;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		this.voucherId = voucherId;
		this.percent = percent;
		this.created = LocalDateTime.now();
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public LocalDateTime getCreated() {
		return created;
	}

	public long discount(long beforeDiscount) {
		return beforeDiscount - (percent / 100) * 100;
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
				"voucherId=" + voucherId +
				", percent=" + percent +
				", created=" + created +
				'}';
	}
}
