package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public class PercentDiscountVoucher implements Voucher {
	private final VoucherType type = VoucherType.PERCENT;
	private final UUID voucherId;
	private final long percent;
	private final LocalDateTime createdAt;

	public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
		if (percent <= 0 || percent > 100) {
			throw new IllegalArgumentException("PercentDiscountVoucher - 퍼센트는 0과 100 사이여야 합니다.");
		}
		this.voucherId = voucherId;
		this.percent = percent;
		this.createdAt = createdAt;
	}

	@Override
	public long getDiscountInfo() {
		return percent;
	}

	@Override
	public VoucherType getType() {
		return type;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public LocalDateTime getCreatedTime() {
		return createdAt;
	}

	@Override
	public long discount(long beforeDiscount) {
		return beforeDiscount * (100 - percent / 100);
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
			"percent=" + percent +
			'}';
	}
}
