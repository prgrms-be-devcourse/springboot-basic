package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.type.VoucherType;

public class FixedAmountVoucher implements Voucher {
	private final UUID voucherId;
	private final long amount;
	private final LocalDateTime createdAt;

	public FixedAmountVoucher(UUID voucherId, long amount) {
		this.voucherId = voucherId;
		this.amount = amount;
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
		return VoucherType.FIX_VOUCHER.name();
	}

	@Override
	public String getDiscountValue() {
		return String.valueOf(this.amount);
	}

	@Override
	public String show() {
		return "voucher type : " + this.getVoucherType() + ", amount : " + amount;
	}

	@Override
	public String toString() {
		return "FixedAmountVoucher{" +
				"voucherId=" + voucherId +
				", amount=" + amount +
				", created=" + createdAt +
				'}';
	}
}
