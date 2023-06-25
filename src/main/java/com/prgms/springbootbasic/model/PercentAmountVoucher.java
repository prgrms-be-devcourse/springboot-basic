package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.OutOfRangePercentException;
import com.prgms.springbootbasic.model.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
	
	private static final long MINIMUM = 0;
	private static final long MAXIMUM = 100;
	private static final String VOUCHER_TYPE = "Percent";
	private static final String FORMAT_STRING = "voucher type : {0} voucher Id : {1} percent : {2}";
	private static final String OUT_OF_RANGE_EXCEPTION_MESSAGE = "Percent voucher have to discount in range of 1 to 100 percent. : ";
	
	private final UUID voucherId;
	private final long percent;
	
	public PercentAmountVoucher(UUID voucherId, long percent) {
		throwWhenOutOfRangePercent(percent);
		this.voucherId = voucherId;
		this.percent = percent;
	}
	
	@Override
	public UUID getVoucherId() {
		return voucherId;
	}
	
	@Override
	public long discount(long beforeAmount) {
		return beforeAmount * (percent / 100);
	}
	
	@Override
	public String toString() {
		return MessageFormat.format(FORMAT_STRING, VOUCHER_TYPE, voucherId, percent);
	}
	
	private void throwWhenOutOfRangePercent(long percent) {
		if (percent <= MINIMUM || percent > MAXIMUM) throw new OutOfRangePercentException(OUT_OF_RANGE_EXCEPTION_MESSAGE + percent);
	}
	
}
