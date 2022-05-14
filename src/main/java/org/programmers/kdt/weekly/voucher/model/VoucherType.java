package org.programmers.kdt.weekly.voucher.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {

	FIXED(1, "원") {
		@Override
		public Voucher create(UUID id, int value) {
			return new FixedAmountVoucher(id, value);
		}

		@Override
		public Voucher create(UUID id, int value, LocalDateTime createdAt) {
			return new FixedAmountVoucher(id, value, createdAt);
		}
	},

	PERCENT(2, "%") {
		@Override
		public Voucher create(UUID id, int value) {
			return new PercentDiscountVoucher(id, value);
		}

		@Override
		public Voucher create(UUID id, int value, LocalDateTime createdAt) {
			return new PercentDiscountVoucher(id, value, createdAt);
		}
	};

	private final int number;
	private final String measure;

	VoucherType(int number, String measure) {
		this.number = number;
		this.measure = measure;
	}

	public String getMeasure() {
		return measure;
	}

	public static VoucherType findByNumber(int number) {
		return Arrays.stream(VoucherType.values())
			.filter(voucher -> voucher.number == number)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid number."));
	}

	public abstract Voucher create(UUID id, int value);

	public abstract Voucher create(UUID id, int value, LocalDateTime createdAt);
}