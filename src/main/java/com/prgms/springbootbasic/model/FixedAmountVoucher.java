package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.UnderMinimumAmountException;
import com.prgms.springbootbasic.model.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
	
	private static final long MINIMUM = 0;
	private static final String VOUCHER_TYPE = "Fixed";
	private static final String FORMAT_STRING = "voucher type : {0} voucher Id : {1} amount : {2}";
	private static final String UNDER_MINIMUM_EXCEPTION_MESSAGE = "Fixed voucher have to discount over 0. amount : ";
	
	private final UUID voucherId;
	private final long amount;
	
	public FixedAmountVoucher(UUID voucherId, long amount) {
		throwWhenUnderMinimum(amount);
		this.voucherId = voucherId;
		this.amount = amount;
	}
	
	@Override
	public UUID getVoucherId() {
		return voucherId;
	}
	
	@Override
	public long discount(long beforeAmount) {
		return beforeAmount - amount;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format(FORMAT_STRING, VOUCHER_TYPE, voucherId, amount);
	}
	
	private void throwWhenUnderMinimum(long amount) {
		if (amount <= MINIMUM) throw new UnderMinimumAmountException(UNDER_MINIMUM_EXCEPTION_MESSAGE + amount);
	}
	
}
