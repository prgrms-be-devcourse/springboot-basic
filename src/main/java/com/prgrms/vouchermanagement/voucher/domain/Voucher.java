package com.prgrms.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public abstract class Voucher implements Discountable{
	private final VoucherType type;
	private final UUID voucherId;
	private final LocalDateTime createdAt;

	public Voucher(VoucherType type, UUID voucherId, LocalDateTime createdAt) {
		checkNull(type, "Voucher : type 은 null 일 수 없습니다");
		checkNull(type, "Voucher : ID는 null 일 수 없습니다");
		checkNull(type, "Voucher : createdAt 은 null 일 수 없습니다 ");

		this.type = type;
		this.voucherId = voucherId;
		this.createdAt = createdAt;
	}

	private void checkNull(Object obj, String message) {
		if (obj == null ){
			throw new IllegalArgumentException(message);
		}
	}

	public UUID getVoucherId(){
		return this.voucherId;
	}

	public VoucherType getType(){
		return this.type;
	}

	public LocalDateTime getCreatedTime(){
		return this.createdAt;
	}

	public abstract long getDiscountInfo();

	public abstract long discount(long beforeDiscount);
}
