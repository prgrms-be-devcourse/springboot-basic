package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import com.prgrms.vouchermanagement.commons.CodeMappable;
import com.prgrms.vouchermanagement.commons.exception.CreationFailException;
import com.prgrms.vouchermanagement.commons.exception.NoMappingOneException;
import com.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public enum VoucherType implements CodeMappable {
	FIXED(1) {
		@Override
		public Voucher getVoucher(UUID voucherId, long fixedAmount, LocalDateTime createdAt) {
			try {
				return new FixedAmountVoucher(voucherId, fixedAmount, createdAt);
			} catch (IllegalArgumentException e) {
				throw new CreationFailException(e);
			}
		}
	},
	PERCENT(2) {
		@Override
		public Voucher getVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
			try {
				return new PercentDiscountVoucher(voucherId, percent, createdAt);
			} catch (IllegalArgumentException e) {
				throw new CreationFailException(e);
			}
		}
	};

	private final int code;

	VoucherType(int code) {
		this.code = code;
	}

	public static VoucherType from(String type) {
		return Arrays.stream(VoucherType.values())
			.filter(constant ->
				constant.name().equalsIgnoreCase(type))
			.findFirst()
			.orElseThrow(() ->
				new NoMappingOneException(type));
	}

	public static VoucherType of(int code) {
		return Arrays.stream(VoucherType.values())
			.filter(constant ->
				constant.getMappingCode() == code)
			.findFirst()
			.orElseThrow(() ->
				new NoMappingOneException(Integer.toString(code)));
	}

	@Override
	public int getMappingCode() {
		return this.code;
	}

	public abstract Voucher getVoucher(UUID voucherId, long percent, LocalDateTime createdAt) throws
		CreationFailException;
}
