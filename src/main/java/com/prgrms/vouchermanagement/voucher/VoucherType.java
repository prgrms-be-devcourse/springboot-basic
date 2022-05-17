package com.prgrms.vouchermanagement.voucher;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
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
	private static final Map<String, VoucherType> str2VoucherTypeMapper;

	static {
		int2VoucherTypeMapper = new HashMap<>();
		str2VoucherTypeMapper = new HashMap<>();

		int2VoucherTypeMapper.put(VoucherType.FIXED.getMappingCode(), VoucherType.FIXED);
		int2VoucherTypeMapper.put(VoucherType.PERCENT.getMappingCode(), VoucherType.PERCENT);

		str2VoucherTypeMapper.put(VoucherType.FIXED.name(), VoucherType.FIXED);
		str2VoucherTypeMapper.put(VoucherType.PERCENT.name(), VoucherType.PERCENT);
	}

	VoucherType(int code) {
		this.code = code;
	}

	@Override
	public int getMappingCode() {
		return this.code;
	}

	@JsonCreator
	public static VoucherType from(String type) {
		Assert.notNull(type, "type 은 null 이 올 수 없습니다");

		return Optional.ofNullable(str2VoucherTypeMapper.get(type.toUpperCase()))
			.orElseThrow(() ->
				new NoMappingOneException(type));
	}

	public static VoucherType of(int code) {
		return Optional.ofNullable(int2VoucherTypeMapper.get(code))
			.orElseThrow(() ->
				new NoMappingOneException(Integer.toString(code)));
	}

	public abstract Voucher getVoucher(UUID voucherId, long percent, LocalDateTime createdAt) throws
		CreationFailException;
}
