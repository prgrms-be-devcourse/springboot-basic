package com.prgrms.vouchermanagement.voucher;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

import com.prgrms.vouchermanagement.commons.CodeMappable;
import com.prgrms.vouchermanagement.commons.exception.NoMappingOneException;
import com.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public enum VoucherType implements CodeMappable {
	FIXED("fixed", FixedAmountVoucher::new),
	PERCENT("percent", PercentDiscountVoucher::new);

	private final String code;
	private final BiFunction<UUID, Long, Voucher> factory;

	VoucherType(String code,
		BiFunction<UUID, Long, Voucher> creatorFunction) {
		this.code = code;
		this.factory = creatorFunction;
	}

	public Voucher getVoucher(UUID id, long voucherDetailsInfo) {
		return factory.apply(id, voucherDetailsInfo);
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
}
