package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.type.VoucherType;

public class PercentDiscountVoucher implements Voucher {
	private final UUID voucherId;
	private final long percent;
	private final LocalDateTime createdAt;

	public PercentDiscountVoucher(UUID voucherId, long percent) {
		this.voucherId = voucherId;
		this.percent = percent;
		this.createdAt = LocalDateTime.now();
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
	public String getVoucherType() {
		return VoucherType.PERCENT_VOUCHER.name();
	}

	@Override
	public String getDiscountValue() {
		return String.valueOf(this.percent);
	}

	@Override
	public String show() {
		return "voucher type : " + getVoucherType() + ", percent : " + percent;
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
				"voucherId=" + voucherId +
				", percent=" + percent +
				", created=" + createdAt +
				'}';
	}
}
