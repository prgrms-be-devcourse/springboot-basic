package com.prgms.springbootbasic.domain;

import com.prgms.springbootbasic.util.VoucherType;

import java.util.UUID;

public interface Voucher {

	VoucherType getVoucherType();
	UUID getVoucherId();
	Long getNumber();

	void changeAmount(long amount);

}
