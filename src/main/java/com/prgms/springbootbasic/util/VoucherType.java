package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.model.FixedAmountVoucher;
import com.prgms.springbootbasic.model.PercentAmountVoucher;
import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.exception.NoSuchVoucherTypeException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum VoucherType {
	
	FIXED("fixed", (amount) -> new FixedAmountVoucher(UUID.randomUUID(), amount), FixedAmountVoucher::new),
	PERCENT("percent", (percent) -> new PercentAmountVoucher(UUID.randomUUID(), percent), PercentAmountVoucher::new);
	
	private static final String ERROR_NO_SUCH_VOUCHER_TYPE = "[Error] Can't Find Such Voucher Type";
	private static final List<VoucherType> VOUCHER_TYPE = Arrays.stream(VoucherType.values()).collect(Collectors.toList());
	private final String type;
	private final Function<Long, Voucher> voucherFunction;
	private final BiFunction<UUID, Long, Voucher> voucherBiFunction;
	
	VoucherType(String type, Function<Long, Voucher> voucherFunction, BiFunction<UUID, Long, Voucher> voucherBiFunction) {
		this.type = type;
		this.voucherFunction = voucherFunction;
		this.voucherBiFunction = voucherBiFunction;
	}
	
	public Voucher createVoucher(long number) {
		return voucherFunction.apply(number);
	}
	
	public Voucher exsistVoucher(UUID voucherId, long number) { return voucherBiFunction.apply(voucherId, number); }
	
	public static VoucherType of(String type) {
		return VOUCHER_TYPE.stream()
				       .filter(vt -> vt.type.equals(type))
				       .findFirst()
				       .orElseThrow(() -> new NoSuchVoucherTypeException(ERROR_NO_SUCH_VOUCHER_TYPE));
	}
	
}
