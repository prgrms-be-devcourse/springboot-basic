package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.util.VoucherType;

import java.util.UUID;

public interface Voucher {

	VoucherType getVoucherType();
	UUID getVoucherId();
	Long getNumber();
	String formatOfCSV();
	
}
