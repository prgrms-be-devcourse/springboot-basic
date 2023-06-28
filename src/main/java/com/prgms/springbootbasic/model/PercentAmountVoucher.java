package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.OutOfRangePercentException;
import com.prgms.springbootbasic.util.ExceptionMessage;
import com.prgms.springbootbasic.util.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
	
	private static final long MINIMUM = 0;
	private static final long MAXIMUM = 100;
	private static final String FORMAT_CSV = "%s,%s,%d\n";
	private static final Logger logger = LoggerFactory.getLogger(PercentAmountVoucher.class);
	
	private final UUID voucherId;
	private final long percent;
	
	public PercentAmountVoucher(UUID voucherId, long percent) {
		throwWhenOutOfRangePercent(percent);
		this.voucherId = voucherId;
		this.percent = percent;
	}

	@Override
	public VoucherType getVoucherType() {
		return VoucherType.PERCENT;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public Long getNumber() { return percent; }
	
	@Override
	public String formatOfCSV() { return String.format(FORMAT_CSV, VoucherType.PERCENT.getType(), voucherId, percent); }
	
	private void throwWhenOutOfRangePercent(long percent) {
		if (percent <= MINIMUM || percent > MAXIMUM) {
			logger.error("Percent 바우처는 0 이하 혹은 100 초과하는 값을 가질 수 없습니다. percent : {}", percent);
			throw new OutOfRangePercentException(ExceptionMessage.OUT_OF_RANGE_PERCENT);
		}
	}
	
}
