package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
	private static final Map<Integer, VoucherType> int2VoucherTypeMapper;

	static {
		int2VoucherTypeMapper = new HashMap<>();
		int2VoucherTypeMapper.put(VoucherType.FIXED.getMappingCode(), VoucherType.FIXED);
		int2VoucherTypeMapper.put(VoucherType.PERCENT.getMappingCode(), VoucherType.PERCENT);

	}

	VoucherType(int code) {
		this.code = code;
	}

	@Override
	public int getMappingCode() {
		return this.code;
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
		return Optional.of(int2VoucherTypeMapper.get(code))
			.orElseThrow(() ->
				new NoMappingOneException(Integer.toString(code)));
	}

	public abstract Voucher getVoucher(UUID voucherId, long percent, LocalDateTime createdAt) throws
		CreationFailException;
}
