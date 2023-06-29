package com.prgms.springbootbasic.voucher.model;

import com.prgms.springbootbasic.voucher.util.VoucherType;

import java.util.UUID;

public interface Voucher {

	VoucherType getVoucherType();
	UUID getVoucherId();
	Long getNumber();
	String formatOfCSV();

}
