package com.prgms.springbootbasic.domain;

import com.prgms.springbootbasic.util.ExceptionMessage;
import com.prgms.springbootbasic.util.VoucherType;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
	
	private static final long MINIMUM = 0;
	
	private final UUID voucherId;
	private long amount;
	
	public FixedAmountVoucher(UUID voucherId, long amount) {
		validateAmount(amount);
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

	@Override
	public void changeAmount(long amount) {
		validateAmount(amount);
		this.amount = amount;
	}

	private void validateAmount(long amount) {
		if (amount <= MINIMUM) {
			throw new IllegalArgumentException(ExceptionMessage.UNDER_MINIMUM_AMOUNT.getMessage());
		}
	}
	
}
