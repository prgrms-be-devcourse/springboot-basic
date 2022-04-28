package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

import com.prgrms.vouchermanagement.commons.CodeMappable;
import com.prgrms.vouchermanagement.commons.exception.CreationFailException;
import com.prgrms.vouchermanagement.commons.exception.NoMappingOneException;
import com.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public enum VoucherType implements CodeMappable {
	FIXED("fixed") {
		@Override
		public Voucher getVoucher(UUID voucherId, long fixedAmount, LocalDateTime createdAt) {
			try {
				return new FixedAmountVoucher(voucherId, fixedAmount, createdAt);
			} catch (IllegalArgumentException e) {
				throw new CreationFailException(e);
			}
		}
	},
	PERCENT("percent") {
		@Override
		public Voucher getVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
			try {
				return new PercentDiscountVoucher(voucherId, percent, createdAt);
			} catch (IllegalArgumentException e) {
				throw new CreationFailException(e);
			}
		}
	};

	private final String code;

	VoucherType(String code) {
		this.code = code;
	}

	public static VoucherType from(String type) {
		return Arrays.stream(VoucherType.values())
			.filter(constant ->
				constant.getMappingCode().equalsIgnoreCase(type))
			.findFirst()
			.orElseThrow(() ->
				new NoMappingOneException(type));
	}

	@Override
	public String getMappingCode() {
		return this.code;
	}

	public abstract Voucher getVoucher(UUID voucherId, long percent, LocalDateTime createdAt) throws
		CreationFailException;
}
