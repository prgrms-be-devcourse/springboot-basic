package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.model.FixedAmountVoucher;
import com.prgms.springbootbasic.model.PercentAmountVoucher;
import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.exception.NoSuchVoucherTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum VoucherType {
	
	FIXED("fixed", (amount) -> new FixedAmountVoucher(UUID.randomUUID(), amount), FixedAmountVoucher::new),
	PERCENT("percent", (percent) -> new PercentAmountVoucher(UUID.randomUUID(), percent), PercentAmountVoucher::new);

	private static final List<VoucherType> VOUCHER_TYPE_VALUES = Arrays.stream(VoucherType.values()).toList();
	private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
	private final String type;
	private final Function<Long, Voucher> voucherFunction;
	private final BiFunction<UUID, Long, Voucher> voucherBiFunction;
	
	VoucherType(String type, Function<Long, Voucher> voucherFunction, BiFunction<UUID, Long, Voucher> voucherBiFunction) {
		this.type = type;
		this.voucherFunction = voucherFunction;
		this.voucherBiFunction = voucherBiFunction;
	}

	public String getType() { return type; }

	public Voucher createVoucher(long number) {
		return voucherFunction.apply(number);
	}
	
	public Voucher exsistVoucher(UUID voucherId, long number) { return voucherBiFunction.apply(voucherId, number); }
	
	public static VoucherType of(String type) {
		return VOUCHER_TYPE_VALUES.stream()
				       .filter(vt -> vt.type.equals(type))
				       .findFirst()
				       .orElseThrow(() -> {
						   logger.warn("해당하는 바우처 타입이 없습니다. 입력한 값 : {}", type);
						   return new NoSuchVoucherTypeException(ExceptionMessage.NO_SUCH_VOUCHER_TYPE);
					   });
	}
	
}
