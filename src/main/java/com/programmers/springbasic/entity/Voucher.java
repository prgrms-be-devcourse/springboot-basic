package com.programmers.springbasic.entity;

import java.util.UUID;

public interface Voucher {
	UUID getVoucherId();
	VoucherType getVoucherType();
	long getAmount();
	long getPercent();
}
