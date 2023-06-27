package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.UnderMinimumAmountException;
import com.prgms.springbootbasic.util.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
	
	private static final long MINIMUM = 0;
	private static final String FORMAT_CSV = "%s,%s,%d\n";
	private static final String UNDER_MINIMUM_EXCEPTION_MESSAGE = "Fixed voucher have to discount over 0. amount : ";
	
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
	@Override
	public long discount(long beforeAmount) {
		return beforeAmount - amount;
	}
	
	@Override
	public byte[] formatOfCSV() { return String.format(FORMAT_CSV, VoucherType.FIXED.getType(), voucherId, amount).getBytes(); }

	private void throwWhenUnderMinimum(long amount) {
		if (amount <= MINIMUM) throw new UnderMinimumAmountException(UNDER_MINIMUM_EXCEPTION_MESSAGE + amount);
	}
	
}
