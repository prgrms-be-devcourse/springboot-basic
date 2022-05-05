package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public abstract class Voucher {
	private final VoucherType type;
	private final UUID voucherId;
	private final LocalDateTime createdAt;
	private final Discountable discounter;
	private final long discountInfo;

	protected Voucher(VoucherType type, UUID voucherId, LocalDateTime createdAt, Discountable discounter,
		long discountInfo) {
		checkNull(type, "Voucher : type 은 null 일 수 없습니다");
		checkNull(voucherId, "Voucher : ID는 null 일 수 없습니다");
		checkNull(createdAt, "Voucher : createdAt 은 null 일 수 없습니다 ");
		checkNull(discounter, "Voucher : discounter 에는 null 이 올 수 없습니다 ");

		this.type = type;
		this.voucherId = voucherId;
		this.createdAt = createdAt;
		this.discounter = discounter;
		this.discountInfo = discountInfo;
	}

	private void checkNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public UUID getVoucherId() {
		return this.voucherId;
	}

	public VoucherType getType() {
		return this.type;
	}

	public LocalDateTime getCreatedTime() {
		return this.createdAt;
	}

	public long getDiscountInfo() {
		return this.discountInfo;
	}

	public long discount(long beforeDiscount) {
		return discounter.evaluate(discountInfo, beforeDiscount);
	}
}
