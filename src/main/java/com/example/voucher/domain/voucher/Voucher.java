package com.example.voucher.domain.voucher;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;

public abstract class Voucher {
	final VoucherType voucherType;
	final Long voucherId;
	final int discountAmount;
	final LocalDateTime createdAt;
	final LocalDateTime updatedAt;

	public Voucher(VoucherType voucherType, Long voucherId, int discountAmount, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.voucherType = voucherType;
		this.voucherId = voucherId;
		this.discountAmount = discountAmount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static Voucher create(VoucherType voucherType, int discountAmount) {
		if (voucherType == FIXED_AMOUNT_VOUCHER) {
			return new FixedAmountVoucher(null, discountAmount);
		}
		return new PercentDiscountVoucher(null, discountAmount);
	}

	public static Voucher create(VoucherType voucherType, Long voucherId, int discountAmount) {
		if (voucherType == FIXED_AMOUNT_VOUCHER) {
			return new FixedAmountVoucher(voucherId, discountAmount);
		}
		return new PercentDiscountVoucher(voucherId,discountAmount);
	}

	public static Voucher create(VoucherType voucherType, Long voucherId, int discountAmount, LocalDateTime createdAt, LocalDateTime updatedAt) {
		if (voucherType == FIXED_AMOUNT_VOUCHER) {
			return new FixedAmountVoucher(voucherId, discountAmount, createdAt, updatedAt);
		}
		return new PercentDiscountVoucher(voucherId, discountAmount, createdAt, updatedAt);
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public abstract int discount(int beforeDiscount);

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Voucher voucher = (Voucher) o;
		return Objects.equals(voucherId, voucher.voucherId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voucherId);
	}
}
