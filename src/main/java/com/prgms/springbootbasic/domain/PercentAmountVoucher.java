package com.prgms.springbootbasic.domain;

import com.prgms.springbootbasic.util.ExceptionMessage;
import com.prgms.springbootbasic.util.VoucherType;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
	
	private static final long MINIMUM = 0;
	private static final long MAXIMUM = 100;
	private final UUID voucherId;
	private long percent;
	
	public PercentAmountVoucher(UUID voucherId, long percent) {
		validatePercent(percent);
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
	public void changeAmount(long percent) {
		validatePercent(percent);
		this.percent = percent;
	}

	private void validatePercent(long percent) {
		if (percent <= MINIMUM || percent > MAXIMUM) {
			throw new IllegalArgumentException(ExceptionMessage.OUT_OF_RANGE_PERCENT.getMessage());
		}
	}
	
}
