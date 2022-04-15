package com.prgrms.vouchermanagement.voucher;

import java.util.UUID;
import java.util.function.BiFunction;

import com.prgrms.vouchermanagement.commons.CodeMappable;
import com.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public enum VoucherType implements CodeMappable {
	FIXED("fixed", (UUID id, Long amount) ->
		new FixedAmountVoucher(id, amount) ),
	PERCENT("percent", (UUID id, Long percent) ->
		new PercentDiscountVoucher(id, percent) );

	private final String code;
	private final BiFunction<UUID, Long, Voucher> factory;

	VoucherType(String code,
		BiFunction<UUID, Long, Voucher> creatorFunction) {
		this.code = code;
		this.factory = creatorFunction;
	}


	@Override
	public boolean isMappedType(String type) {
		return code.equalsIgnoreCase(type);
	}

	public Voucher getVoucher(UUID id, long voucherDetailsInfo){
		return factory.apply(id, voucherDetailsInfo);
	}

}
