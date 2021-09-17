package com.prgrms.w3springboot.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
	private static final int MAX_AMOUNT = 10000;
	private static final int MIN_AMOUNT = 0;

	private final UUID voucherId;
	private final long amount;
	private final VoucherType type;

	public FixedAmountVoucher(UUID voucherId, long amount, VoucherType type) {
		validate(amount);

		this.voucherId = voucherId;
		this.amount = amount;
		this.type = type;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public long getAmount() {
		return amount;
	}

	@Override
	public VoucherType getVoucherType() {
		return VoucherType.FIXED;
	}

	@Override
	public long discount(long beforeDiscount) {
		return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
	}

	@Override
	public void validate(long amount) {
		if (amount < MIN_AMOUNT) {
			throw new IllegalArgumentException("유효한 범위보다 작습니다.");
		}

		if (amount == MIN_AMOUNT) {
			throw new IllegalArgumentException("0을 가질 수 없습니다.");
		}

		if (amount > MAX_AMOUNT) {
			throw new IllegalArgumentException("유효한 범위보다 큽니다.");
		}
	}

	@Override
	public String toString() {
		return "FixedAmountVoucher{" +
			"voucherId=" + voucherId +
			", amount=" + amount +
			'}';
	}
}
