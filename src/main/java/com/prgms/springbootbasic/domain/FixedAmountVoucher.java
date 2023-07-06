package com.prgms.springbootbasic.domain;

import com.prgms.springbootbasic.util.ExceptionMessage;
import com.prgms.springbootbasic.util.VoucherType;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
	
	private static final long MINIMUM = 0;
	
	private final UUID voucherId;
	private final long amount;
	
	public FixedAmountVoucher(UUID voucherId, long amount) {
		throwWhenUnderMinimum(amount);
		this.voucherId = voucherId;
		this.amount = amount;
	}

	@Override
	public VoucherType getVoucherType() {
		return VoucherType.FIXED;
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public Long getNumber() { return amount; }

	private void throwWhenUnderMinimum(long amount) {
		if (amount <= MINIMUM) {
			throw new IllegalArgumentException(ExceptionMessage.UNDER_MINIMUM_AMOUNT.getMessage());
		}
	}
	
}
