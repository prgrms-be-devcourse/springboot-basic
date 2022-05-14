package com.programmers.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

public class FixedVoucher implements Voucher {
	private UUID voucherId;
	private VoucherType voucherType;
	private long discountValue;
	private long quantity;
	private LocalDateTime expirationAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	public FixedVoucher(UUID voucherId, VoucherType voucherType, long discountValue, long quantity,
			LocalDateTime expirationAt,
			LocalDateTime createdAt, LocalDateTime updatedAt) {

		this.voucherId = voucherId;
		this.voucherType = voucherType;
		VoucherType.FIX.checkConstraint(discountValue);
		this.discountValue = discountValue;
		this.quantity = quantity;
		this.expirationAt = expirationAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Override
	public UUID getVoucherId() {
		return this.voucherId;
	}

	@Override
	public VoucherType getVoucherType() {
		return this.voucherType;
	}

	@Override
	public long getDiscountValue() {
		return this.discountValue;
	}

	@Override
	public long getQuantity() {
		return this.quantity;
	}

	@Override
	public LocalDateTime getExpirationAt() {
		return this.expirationAt;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	@Override
	public long discount(long beforeDiscount) {
		long resultPrice = beforeDiscount - this.discountValue;

		if (resultPrice < 0) {
			resultPrice = 0;
		}

		return resultPrice;
	}

	@Override
	public Voucher update(Voucher voucher) {
		VoucherType.PERCENT.checkConstraint(voucher.getDiscountValue());

		this.voucherType = voucher.getVoucherType();
		this.discountValue = voucher.getDiscountValue();
		this.quantity = voucher.getQuantity();
		this.expirationAt = voucher.getExpirationAt();
		this.updatedAt = voucher.getUpdatedAt();

		return this;
	}
}
