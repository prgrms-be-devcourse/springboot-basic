package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.OutOfRangePercentException;
import com.prgms.springbootbasic.util.VoucherType;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
	
	private static final long MINIMUM = 0;
	private static final long MAXIMUM = 100;
	private static final String FORMAT_CSV = "%s,%s,%d\n";
	private static final String OUT_OF_RANGE_EXCEPTION_MESSAGE = "Percent voucher have to discount in range of 1 to 100 percent. : ";
	
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
	public long discount(long beforeAmount) {
		return beforeAmount * (percent / 100);
	}
	
	@Override
	public byte[] formatOfCSV() { return String.format(FORMAT_CSV, VoucherType.PERCENT.getType(), voucherId, percent).getBytes(); }
	
	private void throwWhenOutOfRangePercent(long percent) {
		if (percent <= MINIMUM || percent > MAXIMUM) throw new OutOfRangePercentException(OUT_OF_RANGE_EXCEPTION_MESSAGE + percent);
	}
	
}
